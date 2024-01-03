package otus.java.pro.oop.atm;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cash {
    private final Map<Banknote, Integer> cells = new LinkedHashMap<>();
    private final int minDenomination;

    public Cash(int minDenomination) {
        this.minDenomination = minDenomination;

        cells.put(new Banknote(5000), 0);
        cells.put(new Banknote(1000), 0);
        cells.put(new Banknote(500), 0);
        cells.put(new Banknote(this.minDenomination), 0);
    }

    public int rest() {
        int sum = 0;
        for (Map.Entry<Banknote, Integer> cell : cells.entrySet()) {
            sum += cell.getKey().getDenomination() * cell.getValue();
        }
        return sum;
    }

    private void check(Banknote note, int count) {
        if (!cells.containsKey(note)) {
            throw new IllegalArgumentException("Wrong type of banknotes");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Count can't be less then 0");
        }
    }

    public void load(Banknote note, int count) throws IllegalArgumentException {
        check(note, count);
        cells.put(note, cells.get(note) + count);
    }

    public void loadCash(Cash addCash) {
        for (Map.Entry<Banknote, Integer> item : addCash.cells.entrySet()) {
            load(item.getKey(), item.getValue());
        }
    }

    public int unload(Banknote note, int count) {
        check(note, count);
        if (cells.get(note).compareTo(count) < 0) {
            return 0;
        }
        cells.put(note, cells.get(note) - count);

        return count * note.getDenomination();
    }

    public Cash unloadSum(int sum) throws IllegalArgumentException {
        if (sum % this.minDenomination != 0) {
            throw new IllegalArgumentException("Sum must be multiple " + this.minDenomination);
        }
        if (sum > rest()) {
            throw new IllegalArgumentException("Not enough money at ATM");
        }

        Cash newCash = new Cash(this.minDenomination);

        for (Map.Entry<Banknote, Integer> cell : this.cells.entrySet()) {
            int count = Math.floorDiv(sum, cell.getKey().getDenomination());
            if (count == 0) {
                continue;
            }
            if (count > cell.getValue()) {
                count = cell.getValue();
            }
            newCash.load(cell.getKey(), count);
            sum -= unload(cell.getKey(), count);
        }

        return newCash;
    }

    public String report() {
        StringBuilder b = new StringBuilder("Cash: ");

        for (Map.Entry<Banknote, Integer> cell : this.cells.entrySet()) {
            b.append(cell.getKey().getDenomination()).append(": ").append(cell.getValue()).append("; ");
        }

        return b.toString();
    }

}
