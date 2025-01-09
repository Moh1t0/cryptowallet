package com.javaacademy.cryptowallet.service;

import com.javaacademy.cryptowallet.dto.AccountReplenishmentDto;
import com.javaacademy.cryptowallet.dto.CryptoAccountDto;
import com.javaacademy.cryptowallet.dto.CryptoCreateDto;
import com.javaacademy.cryptowallet.entity.CryptoAccountEntity;
import com.javaacademy.cryptowallet.mapper.CryptoAccountMapper;
import com.javaacademy.cryptowallet.repository.CryptoRepository;
import com.javaacademy.cryptowallet.repository.UserRepository;
import com.javaacademy.cryptowallet.service.interfaces.ConvertToRublesCryptoService;
import com.javaacademy.cryptowallet.service.interfaces.UsdCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoAccountService {
    private final CryptoRepository cryptoRepository;
    private final CryptoAccountMapper mapper;
    private final UserRepository userRepository;
    private final UsdCryptoService usdCryptoService;
    private final ConvertToRublesCryptoService rublesCryptoService;
    private static final int SCALE_EIGHT = 8;

    public CryptoAccountEntity getAccountById(UUID id) {
        if (!cryptoRepository.getCryptoStorage().getData().containsKey(id)) {
            throw new RuntimeException("Счета с id: " + id + "нет !");
        }
        return cryptoRepository.getAccount(id);
    }

    public List<CryptoAccountDto> getAllAccount(String login) {
        List<CryptoAccountDto> accountDto = cryptoRepository
                .getAllAccount(login).stream().map(mapper::convertToDto).collect(Collectors.toList());
        if (accountDto.isEmpty()) {
            throw new IllegalArgumentException("Счета с таким логином нет !");
        }
        return accountDto;
    }

    public CryptoAccountEntity createAccount(CryptoCreateDto createAccount) {
        if (!userRepository.getUserStorage().getData().containsKey(createAccount.getLogin())) {
            throw new RuntimeException("Аккаунта с логином: " + createAccount.getLogin() + " не существует!");
        }
        CryptoAccountEntity account = new CryptoAccountEntity(
                createAccount.getLogin(),
                createAccount.getCurrencyType(),
                BigDecimal.ZERO, UUID.randomUUID());
        cryptoRepository.saveAccount(account);
        return account;
    }

    public String sellCryptoForRubles(AccountReplenishmentDto accountReplenishmentDto) {
        CryptoAccountEntity cryptoAccount = getAccountById(accountReplenishmentDto.getId());
        String description = cryptoAccount.getCryptoCurrencyType().getDescription();

        BigDecimal cryptoPriceInUsd = usdCryptoService.getCryptoPriceInUsd(description);
        BigDecimal insuranceCost = rublesCryptoService.convertRubleToUsd(accountReplenishmentDto.getRublesAmount());
        BigDecimal cryptoToSell = insuranceCost.divide(cryptoPriceInUsd, SCALE_EIGHT, RoundingMode.HALF_UP);

        if (cryptoAccount.getBalance().compareTo(cryptoToSell) < 0) {
            throw new IllegalArgumentException("Недостаточно криптовалюты !");
        }
        cryptoAccount.setBalance(cryptoAccount.getBalance().subtract(cryptoToSell));
        return String.format("Операция успешна, Продано %.10f %s", cryptoToSell, description);
    }

    public BigDecimal getRublesEquivalentBalanceAllAccountsByUser(String userName) {
        List<CryptoAccountEntity> userAccounts = cryptoRepository.getCryptoStorage()
                .getData()
                .values()
                .stream()
                .filter(acount -> Objects.equals(acount.getLogin(), userName))
                .toList();
        if (userAccounts.isEmpty()) {
            throw new RuntimeException("Нет счетов!");
        }
        return userAccounts.stream()
                .map(account -> {
                    String cryptoTypeName = account.getCryptoCurrencyType().getDescription();
                    BigDecimal cryptocurrencyDollars = usdCryptoService.getCryptoPriceInUsd(cryptoTypeName);
                    BigDecimal balanceInDollars = account.getBalance().multiply(cryptocurrencyDollars);
                    return rublesCryptoService.convertUsdToRuble(balanceInDollars);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getAccountBalanceInRubles(UUID uuid) {
        CryptoAccountEntity cryptoAccount = getAccountById(uuid);
        String description = cryptoAccount.getCryptoCurrencyType().getDescription();
        BigDecimal cryptoPriceInUsd = usdCryptoService.getCryptoPriceInUsd(description);
        BigDecimal balanceInUsd = cryptoAccount.getBalance().multiply(cryptoPriceInUsd);
        return rublesCryptoService.convertUsdToRuble(balanceInUsd);
    }

    public void buyCryptoForRubles(AccountReplenishmentDto accountReplenishmentDto) {
        CryptoAccountEntity cryptoAccount = getAccountById(accountReplenishmentDto.getId());
        String description = cryptoAccount.getCryptoCurrencyType().getDescription();

        BigDecimal cryptoPriceInUsd = usdCryptoService.getCryptoPriceInUsd(description);
        BigDecimal insuranceCost = rublesCryptoService.convertRubleToUsd(accountReplenishmentDto.getRublesAmount());

        BigDecimal sum = insuranceCost.divide(cryptoPriceInUsd, SCALE_EIGHT, RoundingMode.HALF_UP);
        cryptoAccount.setBalance(cryptoAccount.getBalance().add(sum));

    }
}
