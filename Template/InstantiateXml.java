import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.*;

public class InstantiateXml {
    private String paramFilePath;
    private ArrayList<Context> contextList;
    private ArrayList<Solution> solutionList;
    private ArrayList<Goal> goalList;
    private ArrayList<Strategy> strategyList;
    private ArrayList<InContextOf> inContextOfLinks;
    private ArrayList<SupportedBy> supportedByLinks;

    public InstantiateXml(String paramFilePath){
        this.paramFilePath = paramFilePath;
        contextList = new ArrayList();
        solutionList = new ArrayList<>();
        goalList = new ArrayList<>();
        strategyList = new ArrayList<>();
        inContextOfLinks = new ArrayList<>();
        supportedByLinks = new ArrayList<>();
    }

    public void CollectContextNodes ()
    {
        File file = new File(this.paramFilePath);
        try{
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String [] params = line.split("\\|");
                if(params.length == 3){
                    if (params[0].equalsIgnoreCase("context")) {
                       Context context = new Context(params[1], params[2]);
                       contextList.add(context);

                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void CollectSolutionNodes ()
    {
        File file = new File(this.paramFilePath);
        try{
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String [] params = line.split("\\|");
                if(params.length == 3){
                    if (params[0].equalsIgnoreCase("solution")) {
                        Solution solution = new Solution(params[1], params[2]);
                        solutionList.add(solution);

                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void CollectGoalNodes ()
    {
        File file = new File(this.paramFilePath);
        try{
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String [] params = line.split("\\|");
                if (params[0].equalsIgnoreCase("maingoal")){
                    if(params.length == 9){
                        MainGoal mainGoal = new MainGoal(params[1], params[2], params[3], params[4],
                                params[5], params[6], params[7],params[8]) ;

                        goalList.add(mainGoal);
                    }
                }

               else if (params[0].equalsIgnoreCase("reqgoal")){
                    if(params.length == 5){
                        ReqGoal reqGoal = new ReqGoal(params[1], params[2], params[3], params[4]) ;

                        goalList.add(reqGoal);
                    }
                }
                else if (params[0].equalsIgnoreCase("modulegoal")){
                    if(params.length == 4){
                        ModuleGoal modGoal = new ModuleGoal(params[1],params[2], params[3]) ;

                        goalList.add(modGoal);
                    }
                }
                else if (params[0].equalsIgnoreCase("oppgoal")){
                    if(params.length == 5){
                        OppGoal oppGoal = new OppGoal(params[1],params[2],params[3], params[4]) ;

                        goalList.add(oppGoal);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void CollectStrategyNodes ()
    {
        File file = new File(this.paramFilePath);
        try{
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String [] params = line.split("\\|");
                if(params.length == 3){
                    if (params[0].equalsIgnoreCase("strategy")) {
                        Strategy strategy = new Strategy(params[1], params[2]);
                        strategyList.add(strategy);

                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void CollectInContextOfLinks ()
    {
        File file = new File(this.paramFilePath);
        try{
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String [] params = line.split("\\|");
                if(params.length == 3){
                    if (params[0].equalsIgnoreCase("incontextof")) {
                        InContextOf inContextOf = new InContextOf(params[1], params[2]);
                        inContextOfLinks.add(inContextOf);

                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void CollectSupportedByLinks ()
    {
        File file = new File(this.paramFilePath);
        try{
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String [] params = line.split("\\|");
                if(params[0].equalsIgnoreCase("supportedby")){
                    if (params.length==3) {
                        SupportedBy supportedBy = new SupportedBy(params[1], params[2]);
                        supportedByLinks.add(supportedBy);

                    }
                    else if (params.length==5) {
                        ProvidesRequires supportedBy = new ProvidesRequires(params[1], params[2], params[3], params[4]);
                        supportedByLinks.add(supportedBy);

                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public ArrayList<Context> getContextList() {
        return contextList;
    }

    public ArrayList<Solution> getSolutionList() {
        return solutionList;
    }

    public ArrayList<Goal> getGoalList() {
        return goalList;
    }

    public ArrayList<Strategy> getStrategyList() {
        return strategyList;
    }

    public ArrayList<InContextOf> getInContextOfLinks() {
        return inContextOfLinks;
    }

    public ArrayList<SupportedBy> getSupportedByLinks() {
        return supportedByLinks;
    }
}
