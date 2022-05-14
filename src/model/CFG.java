package model;

/**
 *
 * @author Daniela Olarte Borja A00368359
 * @author Gabriel Suarez Baron A00368589
 *
 */

public class CFG {

    //Atributes

    private Grammar gm;
    private String grammarM;
    private boolean chomskey = true;

    //Constructor

    public CFG() {
        this.gm = new Grammar();
    }

    /**
     * Gets the grammar input
     * @return grammar
     */

    public Grammar getGrammar() {
        return gm;
    }

    /**
     * Sets the grammar input
     * @param grammar string
     */

    public void getGrammarM(String grammar) {
        grammarM = grammar;
    }

    /**
     * Checks if it is an FNC
     * @return if it is FNC as boolean
     */

    public boolean getChomskey() {
        return chomskey;
    }

    /**
     * Resets grammar
     */

    public void resetGrammar(){
        gm = new Grammar();
    }

    /**
     * Split the grammar with a line break
     */

    public void splitGrammarT() {
        String[] splitGrammarT = grammarM.split("\n");
        splitSymbolInit(splitGrammarT);
    }

    /**
     * Splits the lines according the initial symbol
     * @param splitGrammarT as a string array
     */

    public void splitSymbolInit(String[] splitGrammarT) {
        Transition transition;
        boolean out = true;
        String[] productions = new String[splitGrammarT.length];
        for (int i = 0; i < splitGrammarT.length; i++) {
            String[] init = splitGrammarT[i].split("->");
            init[0] = init[0].replaceAll(" ", "");
            if (init[0].length() == 1) {
                if((int)init[0].charAt(0) >= 65 && (int)init[0].charAt(0) <= 90 && validateNotRepeat(init[0])){
                    transition = new Transition(init[0]);
                    productions[i] = init[1];
                    gm.getTransitions().add(transition);
                } else {
                    System.out.println("NO esta en el rango o valor repetido");
                    chomskey = false;
                    out = false;
                }
            } else {
                System.out.println("Simbolo incial longitud 2");
                out = false;
                chomskey = false;
            }
        }
        if (out) {
            splitProducctions(productions);
        }
    }

    /**
     * Validates if there is not a repeated symbol in the transitions
     * @param symbol string
     * @return in a boolean is there is or not
     */

    public boolean validateNotRepeat(String symbol){
        if(gm.getTransitions().isEmpty()){
            return true;
        } else {
            for (int i = 0; i < gm.getTransitions().size(); i++) {
                if(gm.getTransitions().get(i).getInitSymbol().equals(symbol)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Splits productions with a "|" in order to be checked
     * @param array of productions saved
     */

    public void splitProducctions(String[] productions) {
        int actualT = 0;
        for (int i = 0; i < productions.length; i++) {
            String[] production = productions[i].split("\\|");
            deleteSpace(production);
            validateTerminals(production);
            validateBinary(production);
            addTransitions(actualT, production);
            actualT++;
        }
    }

    /**
     * Deletes spaces in between production
     * @param array of productions saved
     */

    public void deleteSpace(String[] production){
        for (int i = 0; i < production.length; i++) {
            production[i] = production[i].replaceAll(" ", "");
        }
    }

    /**
     * Validates if there is just one terminal per production
     * @param array of productions checked
     */

    public void validateTerminals(String[] production){
        for (int i = 0; i < production.length; i++) {
            if(production[i].length() == 1){
                if(!((int)production[i].charAt(0) >= 97 & (int)production[i].charAt(0) <= 122) ||
                        (int)production[i].charAt(0) == 42){
                    System.out.println("Terminal fuera de rango");
                    chomskey = false;
                }
            }
        }
    }

    /**
     * Validates if there are two variables in a binary production
     * @param array of productions checked
     */

    public void validateBinary(String[] production){
        boolean out = true;
        for (int i = 0; i < production.length && out; i++) {
            if(production[i].length() > 2){
                System.out.println("Produccion no binaria");
                chomskey = false;
                out = false;
            } else if(production[i].length() == 2){
                if(!validateInInit(production[i].charAt(0)+"")){
                    System.out.println("Primer elemento no encontrado en simbolos");
                    chomskey = false;
                    out = false;
                }
                if(!validateInInit(production[i].charAt(1)+"")){
                    System.out.println("Segundo elemento no encontrado en simbolos");
                    chomskey = false;
                    out = false;
                }
            }
        }
    }

    /**
     * Validates initial symbol is valid
     * @param symbol string
     * @return boolean if valid
     */

    public boolean validateInInit(String symbol){
        boolean out = false;
        for (int i = 0; i < gm.getTransitions().size(); i++) {
            if (gm.getTransitions().get(i).getInitSymbol().equals(symbol)) {
                out = true;
                break;
            }
        }
        return out;
    }

    /**
     * Adds transitions to production
     * @param array of productions
     * @param act to check
     */

    public void addTransitions(int act, String[] production){
        if(chomskey){
            for (String s : production) {
                gm.getTransitions().get(act).getProductions().add(s);
            }
        }
    }

    /**
     * Test size of production 
     */

    public void test(){
        System.out.println(gm.getTransitions().size() + "Tamanio");
        for (int i = 0; i < gm.getTransitions().size(); i++) {
            System.out.println(gm.getTransitions().get(i).getInitSymbol());
            for (int j = 0; j < gm.getTransitions().get(i).getProductions().size(); j++) {
                System.out.println("Hace parte del anterior " + gm.getTransitions().get(i).getProductions().get(j));
            }
        }
    }
}
