package consoleapp;

import model.*;

import java.io.IOException;
import java.util.*;

public class ConsoleApp {

    private final BanksService banksService = new BanksService();
    private final CentralBank centralBank = new CentralBank();
    private final Scanner scanner = new Scanner(System.in);

    public void start() throws IOException {
        runMainMenu();
    }

    private void runMainMenu() throws IOException {
        String prompt = "Main Menu";
        String[] options =
                {
                        "Add Client", "Add Bank", "Add Passport to Client",
                        "Add Address to Client", "List Of Clients", "List Of Banks",
                        "Add Credit to Bank", "Add Debit to Bank", "Add Deposit to Bank",
                        "Create an Account", "Add Time", "Add money to Account",
                        "Withdraw from Account", "Transfer from one to another", "Agree For Notifications",
                };

        Menu mainMenu = new Menu(prompt, List.of(options));
        int selectedIndex = mainMenu.run();

        switch (selectedIndex) {
            case 0:
                addClient();
                break;
            case 1:
                addBank();
                break;
            case 2:
                addPassportToClient();
                break;
            case 3:
                addAddressToClient();
                break;
            case 4:
                showListOfClients();
                break;
            case 5:
                showListOfBanks();
                break;
            case 6:
                addCreditToBank();
                break;
            case 7:
                addDebitToBank();
                break;
            case 8:
                addDepositToBank();
                break;
            case 9:
                createAnAccount();
                break;
            case 10:
                addTime();
                break;
            case 11:
                addMoneyToAccount();
                break;
            case 12:
                withdrawFromAccount();
                break;
            case 13:
                transferFromOneToAnother();
                break;
            case 14:
                notificationsAgreement();
                break;
        }
    }

    private void exitGame() throws IOException {
        System.out.println("Press any key to exit...");
        System.in.read();
        System.exit(0);
    }

    private void displayAboutInfo() throws IOException {
        clearConsole();
        System.out.println("Press any key to");
        System.in.read();
        runMainMenu();
    }

    private void addClient() throws IOException {
        clearConsole();
        System.out.println("Enter Client Name");
        String clientName = scanner.next();
        System.out.println("Enter Client Surname");
        String clientSurname = scanner.next();
        System.out.println("Enter Client Money");
        double clientMoney = scanner.nextDouble();

        banksService.addClient(clientName, clientSurname, clientMoney);
        System.out.println("==> You created client " + clientName + " " + clientSurname + " with balance = " + clientMoney);

        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void addBank() throws IOException {
        clearConsole();
        System.out.println("Enter Bank Name");
        String bankName = scanner.next();
        System.out.println("Enter Bank Money");
        double bankMoney = scanner.nextDouble();
        System.out.println("Enter Bank limit for Doubtful Clients");
        double bankLimit = scanner.nextDouble();

        centralBank.addBank(bankName, bankMoney, bankLimit);
        System.out.println("==> You created bank  = " + bankName + " with balance = " + bankMoney + " and Doubtful Clients limit = " + bankLimit);

        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void addPassportToClient() throws IOException {
        clearConsole();

        System.out.println("Select client to set Passport Data\n");

        if (banksService.getClientsList().size() != 0) {
            Client selectedClient = GetClient();

            System.out.println("Enter Passport Data (10 symbols) e.g.: 1234567891");
            int passportData = scanner.nextInt();

            banksService.setClientPassport(selectedClient, passportData);
            System.out.println("Client " + selectedClient.getName() + " has Passport Data: " + passportData);
        } else {
            System.out.println("Clients list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void addAddressToClient() throws IOException {
        clearConsole();

        System.out.println("Select client to set Passport Data\n");

        if (banksService.getClientsList().size() != 0) {
            Client selectedClient = GetClient();

            System.out.println("Enter Address");
            String addressData = scanner.next();

            banksService.setClientAddress(selectedClient, addressData);
            System.out.println("Client " + selectedClient.getName() + " has Address: " + addressData);
        } else {
            System.out.println("Clients list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void notificationsAgreement() throws IOException {
        clearConsole();

        System.out.println("Select client to agree for notifications\n");

        if (banksService.getClientsList().size() != 0) {
            Client selectedClient = GetClient();

            banksService.consentToNotifications(selectedClient);
            System.out.println("Client " + selectedClient.getName() + " has agreed notifications");
        } else {
            System.out.println("Clients list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void showListOfClients() throws IOException {
        clearConsole();
        System.out.println("Select Client to see his data\n");

        if (banksService.getClientsList().size() != 0) {
            Client selectedClient = GetClient();

            System.out.println(
                    "Client " + selectedClient.getName() + " " + selectedClient.getSurname() + " has balance = " + selectedClient.getMoney());
            System.out.println("Client has status " + banksService.checkStatus(selectedClient));
            if (selectedClient.getAddress() != null) {
                System.out.println("Address: " + selectedClient.getAddress());
            }

            if (selectedClient.getPassport() != 0) {
                System.out.println("Passport: " + selectedClient.getPassport());
            }

            if (selectedClient.getNotifications() != null && selectedClient.isAgreeForNotifications()) {
                System.out.println("Notifications:\n");

                for (String notification : selectedClient.getNotifications()) {
                    System.out.println(notification + "\n");
                }
            }

        } else {
            System.out.println("Clients list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private Client GetClient() {
        String prompt = "Select Client";
        List<Client> clientsList = banksService.getClientsList();

        List<String> clientsNames = new ArrayList<>();

        for (Client client : clientsList) {
            clientsNames.add(client.getName());
        }

        List<String> options = new ArrayList<>();

        for (String name : clientsNames) {
            options.add(name);
        }

        Menu mainMenu = new Menu(prompt, options);
        int selectedIndex = mainMenu.run();

        return banksService.getClientsList().get(selectedIndex);
    }

    private BankAccount GetAccount(Client client) {
        String prompt = "Select Account";
        List<BankAccount> accountsList = banksService.getClientsAccounts(client);

        List<UUID> accountsNames = new ArrayList<>();

        for (BankAccount account : accountsList) {
            accountsNames.add(account.getAccountNumber());
        }

        List<String> options = new ArrayList<>();

        for (UUID account : accountsNames) {
            options.add(account.toString());
        }

        Menu mainMenu = new Menu(prompt, options);
        int selectedIndex = mainMenu.run();

        return banksService.getBankAccounts().get(selectedIndex);
    }

    private Bank GetBank() {
        String prompt = "Select Bank";
        List<Bank> banksList = centralBank.getBanks();
        System.out.println("Amount of banks = " + banksList.size());

        List<String> banksNames = new ArrayList<>();

        for (Bank bank : banksList) {
            banksNames.add(bank.getName());
        }

        List<String> options = new ArrayList<>();

        for (String name : banksNames) {
            options.add(name);
        }

        Menu mainMenu = new Menu(prompt, options);
        int selectedIndex = mainMenu.run();

        return centralBank.getBanks().get(selectedIndex);
    }

    private void showListOfBanks() throws IOException {
        clearConsole();

        System.out.println("Select Bank to see his data\n");

        if (centralBank.getBanks().size() != 0) {
            Bank selectedBank = GetBank();

            System.out.println(
                    "Bank " + selectedBank.getName() + " has balance = " + selectedBank.getMoney() + " and TransactionLimit = " + selectedBank.getMaxLimitForDoubtfulClients());

            if (selectedBank.getCreditLimit() != 0) {
                System.out.println("Credit Limit: " + selectedBank.getCreditLimit() + "\n");
            }

            if (selectedBank.getCreditCommission() != 0) {
                System.out.println("Credit Commission: " + selectedBank.getCreditCommission() + "\n");
            }

            if (selectedBank.getCreditCommission() != 0) {
                System.out.println("Debit Interest: " + selectedBank.getDebitInterest() + "\n");
            }

            if (selectedBank.getDepositInterest() != null) {
                System.out.println("Deposit Interest:");

                for (InterestPair interest : selectedBank.getDepositInterest()) {
                    System.out.println("=>For summ " + interest.getSum() + " interest is " + interest.getInterest());
                }
            }

        } else {
            System.out.println("Banks list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void addCreditToBank() throws IOException {
        clearConsole();

        System.out.println("Select Bank to set Credit Data\n");

        if (centralBank.getBanks().size() != 0) {
            Bank selectedBank = GetBank();

            System.out.println("Enter Credit Limit");
            double creditLimit = scanner.nextDouble();

            System.out.println("Enter Credit Commission");
            double creditCommission = scanner.nextDouble();

            centralBank.addCreditToBank(selectedBank, creditLimit, creditCommission);
            System.out.println(
                    "Bank " + selectedBank.getName() + " has Credit Limit = " + creditLimit + " and Credit Commission = " + creditCommission);

        } else {
            System.out.println("Banks list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void addDebitToBank() throws IOException {
        clearConsole();

        System.out.println("Select Bank to set Debit Data\n");

        if (centralBank.getBanks().size() != 0) {
            Bank selectedBank = GetBank();

            System.out.println("Enter Debit Interest");
            double debitInterest = scanner.nextDouble();

            centralBank.addDebitToBank(selectedBank, debitInterest);
            System.out.println("Bank " + selectedBank.getName() + " has Debit Interest = " + debitInterest);

        } else {
            System.out.println("Banks list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void addDepositToBank() throws IOException {
        clearConsole();

        System.out.println("Select Bank to set Deposit Data\n");

        if (centralBank.getBanks().size() != 0) {
            Bank selectedBank = GetBank();

            System.out.println("Enter Deposit Interest");
            List<InterestPair> depositInterest = new ArrayList<>();
            String pause = "y";

            while (Objects.equals(pause, "y")) {
                System.out.println("Enter Min Summ For Interest");
                double summ = scanner.nextDouble();
                System.out.println("Enter Interest");
                double interest = scanner.nextDouble();
                depositInterest.add(new InterestPair(summ, interest));

                System.out.println("Do you want to continue? (y/n)");
                pause = scanner.next();
            }

            centralBank.addDepositToBank(selectedBank, depositInterest);

            System.out.println("Bank + " + selectedBank.getName() + " has Debit Interest:");

            if (selectedBank.getDepositInterest().size() != 0) {
                for (InterestPair interest : selectedBank.getDepositInterest()) {
                    System.out.println("=>For summ " + interest.getSum() + " interest is " + interest.getInterest());
                }
            }

        } else {
            System.out.println("Banks list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void createAnAccount() throws IOException {
        clearConsole();

        System.out.println("Select client to create account\n");

        if (banksService.getClientsList().size() != 0) {
            Client selectedClient = GetClient();
            Bank selectedBank = GetBank();

            clearConsole();
            String[] options2 =
                    {
                            "Debit", "Credit", "Deposit",
                    };

            String prompt2 = "Select type of account to create";
            Menu accountMenu = new Menu(prompt2, List.of(options2));
            int selectedIndex2 = accountMenu.run();

            clearConsole();

            if (selectedIndex2 == 0) {
                System.out.println("Enter amount of money for this account");
                double money = scanner.nextDouble();
                banksService.createDebitAccount(selectedClient, selectedBank, money);
                System.out.println("Client " + selectedClient.getName() + " has Debit account in Bank " + selectedBank.getName());
            } else if (selectedIndex2 == 1) {
                banksService.createCreditAccount(selectedClient, selectedBank);
                System.out.println("Client " + selectedClient.getName() + " has Credit account in Bank " + selectedBank.getName());
            } else {
                System.out.println("Enter amount of money for this account");
                double money = scanner.nextDouble();
                System.out.println("Enter term for this account");
                int term = scanner.nextInt();

                banksService.createDepositAccount(selectedClient, selectedBank, money, term);
                System.out.println("Client " + selectedClient.getName() + " has Deposit account in Bank " + selectedClient.getName());
            }

        } else {
            System.out.println("Clients list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void addTime() throws IOException {
        clearConsole();
        System.out.println("Add some days\n");
        System.out.println("How many days do you want do add");
        int days = scanner.nextInt();

        for (BankAccount account : banksService.getBankAccounts()) {
            System.out.println("Client " + account.getClient().getName() + " Has " + account.getMoney() + " on " + account.getType() + " Account with id " + account.getAccountNumber() + " in " + account.getBank().getName() + " bank");
        }

        System.out.println("\n\nPress any key to see the result of adding time\n\n");
        System.in.read();

        banksService.addTime(days);

        for (BankAccount account : banksService.getBankAccounts()) {
            System.out.println("Client " + account.getClient().getName() + " Has " + account.getMoney() + " on " + account.getType() + " Account with id " + account.getAccountNumber() + " in " + account.getBank().getName() + " bank");
        }

        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void addMoneyToAccount() throws IOException {
        clearConsole();

        System.out.println("Select Client and Account to Add Money\n");

        if (banksService.getClientsList().size() != 0 && banksService.getBankAccounts().size() != 0) {
            Client selectedClient = GetClient();
            BankAccount selectedAccount = GetAccount(selectedClient);

            System.out.println("Enter amount of money to put");
            double money = scanner.nextDouble();

            banksService.putMoneyIntoAccount(selectedAccount, money);

            System.out.println("Account + " + selectedAccount.getAccountNumber() + " has money: " + selectedAccount.getMoney());
        } else {
            System.out.println("Clients list or Accounts list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void withdrawFromAccount() throws IOException {
        clearConsole();

        System.out.println("Select Client and Account to take money from\n");

        if (banksService.getClientsList().size() != 0 && banksService.getBankAccounts().size() != 0) {
            Client selectedClient = GetClient();
            BankAccount selectedAccount = GetAccount(selectedClient);

            System.out.println("Enter amount of money to get");
            double money = scanner.nextDouble();

            banksService.takeMoneyFromAccount(selectedAccount, money);

            System.out.println("Account " + selectedAccount.getAccountNumber() + " has money: " + selectedAccount.getMoney());
        } else {
            System.out.println("Clients list or Accounts list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    private void transferFromOneToAnother() throws IOException {

        if (banksService.getClientsList().size() != 0 && banksService.getBankAccounts().size() > 1) {
            System.out.println("Select Client and Account to take money from\n");
            Client selectedClient1 = GetClient();
            BankAccount selectedAccount1 = GetAccount(selectedClient1);

            System.out.println("Select Client and Account to transfer money to\n");
            Client selectedClient2 = GetClient();
            BankAccount selectedAccount2 = GetAccount(selectedClient2);

            System.out.println("Enter amount of money");
            double money = scanner.nextDouble();

            centralBank.transferMoney(selectedAccount1, selectedAccount2, money);

            System.out.println("Account " + selectedAccount1.getAccountNumber() + " has money: " + selectedAccount1.getMoney());
            System.out.println("Account " + selectedAccount2.getAccountNumber() + " has money: " + selectedAccount2.getMoney());
        } else {
            System.out.println("Clients list or Accounts list is empty");
        }
        System.out.println("\nPress any key to return to Main Menu");
        System.in.read();
        runMainMenu();
    }

    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException ignored) {
        }
    }
}