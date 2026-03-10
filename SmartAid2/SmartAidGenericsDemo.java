import java.util.*;

interface GenericStore<T> {
    void addItem(T item);
    T getItem(int index);
    boolean searchItem(T item);
}

class DisasterReliefStore<T> implements GenericStore<T> {
    private ArrayList<T> items = new ArrayList<>();

    @Override
    public void addItem(T item) {
        items.add(item);
    }

    @Override
    public T getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    @Override
    public boolean searchItem(T item) {
        return items.contains(item);
    }

    public ArrayList<T> getAllItems() {
        return items;
    }
}

class DisplayUtil {
    public static <T> void displayList(List<T> list) {
        for (T element : list) {
            System.out.println(element);
        }
        System.out.println();
    }
}

class ReliefRequest {
    private String centerName;
    private String requiredItem;
    private int quantity;

    public ReliefRequest(String centerName, String requiredItem, int quantity) {
        this.centerName = centerName;
        this.requiredItem = requiredItem;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Center: " + centerName + ", Item: " + requiredItem + ", Quantity: " + quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ReliefRequest)) return false;
        ReliefRequest other = (ReliefRequest) obj;
        return this.centerName.equals(other.centerName) &&
               this.requiredItem.equals(other.requiredItem) &&
               this.quantity == other.quantity;
    }
}

public class SmartAidGenericsDemo {
    public static void main(String[] args) {

        DisasterReliefStore<ReliefRequest> reliefStore = new DisasterReliefStore<>();

        reliefStore.addItem(new ReliefRequest("Relief Center A", "Food Packets", 100));
        reliefStore.addItem(new ReliefRequest("Relief Center B", "Medicines", 50));
        reliefStore.addItem(new ReliefRequest("Relief Center C", "Blankets", 200));

        System.out.println("Relief Request at index 1:");
        System.out.println(reliefStore.getItem(1));
        System.out.println();

        ReliefRequest searchReq = new ReliefRequest("Relief Center A", "Food Packets", 100);
        System.out.println("Searching for Relief Center A's request:");
        System.out.println(reliefStore.searchItem(searchReq) ? "Found" : "Not Found");
        System.out.println();

        System.out.println("All Relief Requests:");
        DisplayUtil.displayList(reliefStore.getAllItems());

        List<String> volunteers = Arrays.asList("Arya", "Rahul", "Fatima");
        System.out.println("Volunteers Available:");
        DisplayUtil.displayList(volunteers);

        List<Integer> priorityLevels = Arrays.asList(1, 3, 2);
        System.out.println("Request Priority Levels:");
        DisplayUtil.displayList(priorityLevels);
    }
}
