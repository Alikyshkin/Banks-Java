import exception.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    private BanksService banksService;
    private CentralBank centralBank;

    @BeforeEach
    public void setUp() {
        banksService = new BanksService();
        centralBank = new CentralBank();
    }

    @Test
    public void AddBankAndClient() {
        String clientName = "Name";
        String clientSurname = "Surname";
        double clientMoney = 100_000;
        String clientAddress = "Address";
        int clientPassport = 12_34_567891;

        String bankName = "Tinkoff";
        double bankMoney = 1_000_000;
        double maxLimitForDoubtfulClients = 100_000;

        Client client = banksService.addClient(clientName, clientSurname, clientMoney);

        assertEquals(client.getPassport(), 0);
        banksService.setClientAddress(client, clientAddress);

        banksService.setClientPassport(client, clientPassport);

        Bank bank = centralBank.addBank(bankName, bankMoney, maxLimitForDoubtfulClients);

        assertEquals(client.getName(), clientName);
        assertEquals(client.getMoney(), clientMoney);
        assertEquals(1, centralBank.getBanks().size());
    }

    @Test
    public void ClientCanGetDebit() {
        String clientName = "Name";
        String clientSurname = "Surname";
        double clientMoney = 500_000;
        String clientAddress = "Address";
        int clientPassport = 12_34_567891;

        String bankName = "Tinkoff";
        double bankMoney = 1_000_000;
        double maxLimitForDoubtfulClients = 100_000;

        Client client = banksService.addClient(clientName, clientSurname, clientMoney);
        banksService.setClientAddress(client, clientAddress);
        banksService.setClientPassport(client, clientPassport);

        Bank bank = centralBank.addBank(bankName, bankMoney, maxLimitForDoubtfulClients);
        centralBank.addDebitToBank(bank, 3.65);

        BankAccount debitAccount = banksService.createDebitAccount(client, bank, 100_000);

        banksService.addTime(5);
        assertEquals(debitAccount.getMoney(), 100_000);

        banksService.putMoneyIntoAccount(debitAccount, 500);

        banksService.addTime(26);
        assertEquals(debitAccount.getMoney(), 100_801.25);
    }

    @Test

    public void ClientCanGetDeposit() {
        String clientName = "Name";
        String clientSurname = "Surname";
        double clientMoney = 500_000;
        String clientAddress = "Address";
        int clientPassport = 12_34_567891;

        String bankName = "Tinkoff";
        double bankMoney = 1_000_000;
        double maxLimitForDoubtfulClients = 100_000;

        Client client = banksService.addClient(clientName, clientSurname, clientMoney);
        banksService.setClientAddress(client, clientAddress);
        banksService.setClientPassport(client, clientPassport);

        Bank bank = centralBank.addBank(bankName, bankMoney, maxLimitForDoubtfulClients);
        List<InterestPair> depositInterest = List.of(
                new InterestPair(0, 3),
                new InterestPair(100_000, 3.65),
                new InterestPair(150_000, 4)
        );
        centralBank.addDepositToBank(bank, depositInterest);

        BankAccount depositAccount = banksService.createDepositAccount(client, bank, 100_000, 100);

        banksService.addTime(5);
        assertEquals(depositAccount.getMoney(), 100_000);

        banksService.putMoneyIntoAccount(depositAccount, 500);

        banksService.addTime(26);
        assertEquals(depositAccount.getMoney(), 100_801.25);

        assertThrows(
                NotExpiredTermException.class,
                () -> banksService.takeMoneyFromAccount(depositAccount, 100)
        );
    }

    @Test
    public void ClientCanGetCredit() {
        String clientName = "Name";
        String clientSurname = "Surname";
        double clientMoney = 500_000;
        String clientAddress = "Address";
        int clientPassport = 12_34_567891;
        double maxLimitForDoubtfulClients = 100_000;

        String bankName = "Tinkoff";
        double bankMoney = 1_000_000;

        Client client = banksService.addClient(clientName, clientSurname, clientMoney);
        banksService.setClientAddress(client, clientAddress);
        banksService.setClientPassport(client, clientPassport);

        assertEquals(client.getMoney(), 500_000);

        Bank bank = centralBank.addBank(bankName, bankMoney, maxLimitForDoubtfulClients);
        centralBank.addCreditToBank(bank, 50_000, 3.65);

        BankAccount creditAccount = banksService.createCreditAccount(client, bank);
        assertEquals(client.getMoney(), 500_000);

        banksService.addTime(5);
        assertEquals(creditAccount.getMoney(), 50_000);
        assertEquals(creditAccount.getCollected(), 0);

        banksService.takeMoneyFromAccount(creditAccount, 30_000);

        banksService.addTime(26);
        assertEquals(creditAccount.getClient().getMoney(), 530_000);
        assertEquals(creditAccount.getCollected(), 78, 0.1);
    }

    @Test
    public void ClientCanCancelTheLastTransaction() {
        String clientName = "Name";
        String clientSurname = "Surname";
        double clientMoney = 500_000;
        String clientAddress = "Address";
        int clientPassport = 12_34_567891;

        String bankName = "Tinkoff";
        double bankMoney = 1_000_000;
        double maxLimitForDoubtfulClients = 100_000;

        Client client = banksService.addClient(clientName, clientSurname, clientMoney);
        banksService.setClientAddress(client, clientAddress);
        banksService.setClientPassport(client, clientPassport);

        Bank bank = centralBank.addBank(bankName, bankMoney, maxLimitForDoubtfulClients);
        centralBank.addDebitToBank(bank, 3.65);

        BankAccount debitAccount = banksService.createDebitAccount(client, bank, 100_000);

        assertEquals(debitAccount.getMoney(), 100_000);
        assertEquals(client.getMoney(), 400_000);

        banksService.putMoneyIntoAccount(debitAccount, 500);
        assertEquals(debitAccount.getMoney(), 100_500);
        assertEquals(client.getMoney(), 399_500);

        banksService.cancelLastTransaction(debitAccount);
        assertEquals(debitAccount.getMoney(), 100_000);
        assertEquals(client.getMoney(), 400_000);
    }
}
