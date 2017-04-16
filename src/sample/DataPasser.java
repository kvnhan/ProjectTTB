package sample;

/**
 * passes data to the database. Contains an instance of AlcoholData.
 */
public class DataPasser {
    private static AlcoholData alcData;

    public AlcoholData getAlcData() {
        return alcData;
    }

    public void setAlcData(AlcoholData alcData) {
        this.alcData = alcData;
    }
}
