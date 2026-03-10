import java.util.*;
import java.util.stream.*;

class ReliefUpdate {
    private String centerName;
    private String updateMessage;
    private int length;

    public ReliefUpdate(String centerName, String updateMessage) {
        this.centerName = centerName;
        this.updateMessage = updateMessage;
        this.length = updateMessage.length();
    }

    public String getCenterName() { return centerName; }
    public String getUpdateMessage() { return updateMessage; }
    public int getLength() { return length; }

    @Override
    public String toString() {
        return "[" + centerName + "]: " + updateMessage;
    }
}

public class SmartAidLambdaStreamDemo {

    public static void main(String[] args) {

        Runnable greet = () -> System.out.println("📢 Relief Update Monitoring System Activated!");
        greet.run();

        java.util.function.Consumer<String> alertAdmin =
                admin -> System.out.println("🚨 Alert sent to: " + admin);
        alertAdmin.accept("Disaster Management Head Office");

        java.util.function.BiFunction<String, String, ReliefUpdate> createUpdate =
                (center, updateMsg) -> new ReliefUpdate(center, updateMsg);

        java.util.function.Function<ReliefUpdate, String> analyzeUpdate =
                (u) -> {
                    String remark = u.getLength() > 25 ? "High Priority / Long Update" : "Short Update";
                    return u + " ➜ [" + remark + "]";
                };

        List<ReliefUpdate> updates = Arrays.asList(
            createUpdate.apply("Center A", "Need 100 food packets immediately."),
            createUpdate.apply("Center B", "Water supply running low."),
            createUpdate.apply("Center C", "Medical team required urgently."),
            createUpdate.apply("Center A", "Blankets and tents needed for 80 families."),
            createUpdate.apply("Center B", "Situation under control now.")
        );

        System.out.println("\n--- FILTER: Updates from Center A ---");
        List<ReliefUpdate> centerAUpdates = updates.stream()
                .filter(u -> u.getCenterName().equals("Center A"))
                .collect(Collectors.toList());
        centerAUpdates.forEach(System.out::println);

        System.out.println("\n--- MAP: Convert to update messages only ---");
        List<String> messages = updates.stream()
                .map(ReliefUpdate::getUpdateMessage)
                .collect(Collectors.toList());
        messages.forEach(System.out::println);

        System.out.println("\n--- SORT: Sort updates by message length ---");
        List<ReliefUpdate> sortedUpdates = updates.stream()
                .sorted(Comparator.comparingInt(ReliefUpdate::getLength))
                .collect(Collectors.toList());
        sortedUpdates.forEach(System.out::println);

        System.out.println("\n--- REDUCE: Total characters in all updates ---");
        int totalCharacters = updates.stream()
                .map(ReliefUpdate::getLength)
                .reduce(0, Integer::sum);
        System.out.println("Total Characters: " + totalCharacters);

        System.out.println("\n--- COLLECT: Group updates by Center ---");
        Map<String, List<ReliefUpdate>> updatesByCenter = updates.stream()
                .collect(Collectors.groupingBy(ReliefUpdate::getCenterName));
        updatesByCenter.forEach((center, list) ->
                System.out.println(center + " → " + list));

        System.out.println("\n--- BLOCK LAMBDA ANALYSIS ---");
        updates.forEach(u -> System.out.println(analyzeUpdate.apply(u)));

        System.out.println("\n--- UNIQUE RELIEF CENTERS ---");
        Set<String> uniqueCenters = updates.stream()
                .map(ReliefUpdate::getCenterName)
                .collect(Collectors.toSet());
        System.out.println(uniqueCenters);
    }
}