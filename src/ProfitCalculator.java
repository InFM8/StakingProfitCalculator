
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProfitCalculator {

    public static void calculateAndWriteStakingProfitToCsv(String fileName, double initialInvestment, double rewardRate, LocalDate startDate, int duration, int rewardDay, boolean reinvest) {
        double dailyStakingReward = (rewardRate / 100) / 365;
        LocalDate endDate = startDate.plusMonths(duration);
        int rewards = 0;
        double totalProfit = 0;
        int remainingMonth = duration;

        LocalDate rewardDate = startDate.withDayOfMonth(rewardDay);
        int daysInMonth = startDate.until(rewardDate).getDays();

        try (FileWriter csvWriter = new FileWriter(fileName + ".csv")) {
            csvWriter.append("Line #,Reward Date,Investment Amount,Reward Amount,Total Reward Amount To Date,Staking Reward Rate\n");
            while (remainingMonth != 0) {

                rewardDate = startDate.withDayOfMonth(rewardDay);

                double rewardAmount = calculateRewardAmount(initialInvestment, dailyStakingReward, daysInMonth);
                totalProfit += rewardAmount;
                rewards++;

                writeDataToCsv(csvWriter, rewards, rewardDate, initialInvestment, rewardAmount, totalProfit, rewardRate);

                if (reinvest) {
                    initialInvestment += rewardAmount;
                }

                remainingMonth--;
                if (remainingMonth == 0) {
                    int lastRewardAmount = (int) ChronoUnit.DAYS.between(startDate, endDate);

                    totalProfit += lastRewardAmount * dailyStakingReward * initialInvestment;
                    rewards++;
                    writeDataToCsv(csvWriter, rewards, endDate, initialInvestment, lastRewardAmount * dailyStakingReward * initialInvestment, totalProfit, rewardRate);

                    if (reinvest) {
                        initialInvestment += lastRewardAmount * dailyStakingReward * initialInvestment;
                    }
                }

                startDate = rewardDate.plusMonths(1);
                daysInMonth = startDate.minusMonths(1).lengthOfMonth();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDataToCsv(FileWriter csvWriter, int rewards, LocalDate rewardDate, double initialInvestment, double rewardAmount, double totalProfit, double yearlyStakingReward) throws IOException {
        csvWriter.append(String.format("%d,%s,%.6f,%.6f,%.6f,%.2f%%\n", rewards, rewardDate, initialInvestment, rewardAmount, totalProfit, yearlyStakingReward));
    }

    private static double calculateRewardAmount(double initialInvestment, double dailyStakingReward, int daysInMonth) {
        return dailyStakingReward * initialInvestment * daysInMonth;
    }

    public static void main(String[] args) {

        ValidationsAndInputs validations = new ValidationsAndInputs();

        String fileName = validations.getValidatedFileName();
        double initialInvestment = validations.getValidatedInitialInvestment();
        double rewardRate = validations.getValidatedRewardRate();
        LocalDate startDate = validations.getValidatedStartDate();
        int stakingDuration = validations.getValidatedDuration();
        int rewardPaymentDay = validations.getValidatedRewardDay();
        boolean reinvest = validations.isSelectedReinvest();

        if (fileName != null && initialInvestment > 0 && rewardRate > 0 && startDate != null && stakingDuration > 0 && rewardPaymentDay > 0) {
            calculateAndWriteStakingProfitToCsv(fileName, initialInvestment, rewardRate, startDate, stakingDuration, rewardPaymentDay, reinvest);
            System.out.println("\nYour CSV file has been successfully created and is ready to use.");
        }
    }
}