import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ValidationsAndInputs {
    private Scanner scanner;
    private final String regex = "^[a-zA-Z0-9]*$";

    public ValidationsAndInputs() {
        scanner = new Scanner(System.in);
    }

    public String getValidatedFileName() {
        String fileName = "";
        while (!isValidatedFileName(fileName)) {
            System.out.print("Enter a file name: ");
            fileName = scanner.nextLine();
            if (!isValidatedFileName(fileName)) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return fileName;
    }

    private boolean isValidatedFileName(String fileName) {
        return !fileName.isEmpty() && fileName.matches(regex);
    }

    public double getValidatedInitialInvestment() {
        double initialInvestment = 0;
        while (!isValidatedInvestment(initialInvestment)) {
            System.out.print("Enter initial investment amount (e.g. 10 ETH) : ");
            try {
                initialInvestment = Double.parseDouble(scanner.next());
                if (!isValidatedInvestment(initialInvestment)) {
                    System.out.println("Initial investment amount must be greater than 0. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number. ");
            }
        }
        return initialInvestment;
    }

    private boolean isValidatedInvestment(double investment) {
        return investment > 0;
    }

    public double getValidatedRewardRate() {
        double rewardRate = 0;
        while (!isValidatedRewardRate(rewardRate)) {
            System.out.print("Enter yearly staking reward rate in % (e.g. 7%) : ");
            try {
                rewardRate = Double.parseDouble(scanner.next());
                if (!isValidatedRewardRate(rewardRate)) {
                    System.out.println("Yearly staking reward rate must be greater than 0 and less than or equal to 100. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number. ");
            }
        }
        return rewardRate;
    }

    private boolean isValidatedRewardRate(double rewardRate) {
        return rewardRate > 0 && rewardRate <= 100;
    }

    public LocalDate getValidatedStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = null;
        while (startDate == null) {
            System.out.print("Enter staking start date (e.g. 2020-11-10) : ");
            String input = scanner.next();
            if (isValidatedStartDate(input)) {
                startDate = LocalDate.parse(input, formatter);
            } else {
                System.out.println("Invalid input. Please enter a valid date in the format yyyy-MM-dd. ");
            }
        }
        return startDate;
    }

    private boolean isValidatedStartDate(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(startDate, formatter);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public int getValidatedDuration() {
        int duration = 0;
        while (duration <= 0) {
            System.out.print("Enter staking duration in months (e.g. 24 months) : ");
            String input = scanner.next();
            if (isValidatedDuration(input)) {
                duration = Integer.parseInt(input);
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return duration;
    }

    private boolean isValidatedDuration(String duration) {
        try {
            int durationInt = Integer.parseInt(duration);
            if (durationInt > 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getValidatedRewardDay() {
        int rewardDay = 0;
        while (rewardDay <= 0 || rewardDay > 31) {
            System.out.print("Enter reward payment day (e.g. every 15th day of the month): ");
            String input = scanner.next();
            if (isValidatedRewardDay(input)) {
                rewardDay = Integer.parseInt(input);
            } else {
                System.out.println("Invalid input. Reward payment day must be between 1 and 31. Please try again.");
            }
        }
        return rewardDay;
    }

    private boolean isValidatedRewardDay(String rewardDay) {
        try {
            int rewardDayInt = Integer.parseInt(rewardDay);
            if (rewardDayInt > 0 && rewardDayInt <= 31) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isSelectedReinvest() {
        boolean reinvest = false;
        while (true) {
            System.out.print("Reinvest rewards? (e. g. yes) : ");
            String input = scanner.next();
            if (isValidatedReinvest(input)) {
                if (input.equalsIgnoreCase("yes")) {
                    reinvest = true;
                }
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
        return reinvest;
    }

    private boolean isValidatedReinvest(String input) {
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")) {
            return true;
        } else {
            return false;
        }
    }
}
