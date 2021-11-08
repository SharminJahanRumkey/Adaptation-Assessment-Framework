package edu.utulsa.sims.components.environment;

        import edu.utulsa.components.ComponentEnvironment;
        import edu.utulsa.sims.components.state.MeasureState;

        import java.io.FileReader;
        import java.io.IOException;
        import java.io.LineNumberReader;

/**
 * Simulates the component-local environment for the Measure component. This class is responsible for updating the
 * sensor pressure value.
 */
public class MeasureEnvironment implements ComponentEnvironment<MeasureState> {
    int count=0;
    @Override
    public void update(MeasureState state) {
        // TODO: This method should update the pressure value, either randomly or based on a scenario input file.
        try {
            count++;
            setPressure(count, state);
        }catch (IOException e){}
    }

    public void setPressure(int i,MeasureState state ) throws IOException {
        String csvFile = "./input.txt";
        String line = "";
        String inputValue="";
        try (LineNumberReader rdr = new LineNumberReader(new FileReader(csvFile))) {

            for (line = null; (line = rdr.readLine()) != null;) {
                if (rdr.getLineNumber() == i && !line.isEmpty()) {
                    state.setPressure(Double.parseDouble(line));
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
