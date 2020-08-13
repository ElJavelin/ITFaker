package org.smirnov;

import java.util.Random;

public class DataGenerator {

    private String locale;
    private int numOfRecords;
    private float numOfErrors = 0;

    Parser parser = new Parser();
    Random random = new Random();

    String [][] curDataArr = new String[19][5000];
    int [] sizeArr = new int[19];
    String [] resultArr = new String[14];

    public DataGenerator(String locale, String numOfRecords, String numOfErrors) {
        if (tryParseSArg(numOfRecords) || tryParseTArg(numOfErrors)) throw new IllegalArgumentException();
        this.locale = locale;
        this.numOfRecords = Integer.parseInt(numOfRecords);
        this.numOfErrors = Float.parseFloat(numOfErrors);
    }

    public DataGenerator(String locale, String numOfRecords) {
        if (tryParseSArg(numOfRecords)) throw new IllegalArgumentException();
        this.locale = locale;
        this.numOfRecords = Integer.parseInt(numOfRecords);
    }

    public void begin(){
        parser.xmlParser(locale);
        curDataArr = parser.getDataArr().clone();
        sizeArr = parser.getSizeArr().clone();
        String cleanString;
        int resultArrSize;
        for (int i = 0; i < numOfRecords; i++){
            genData();
            resultArrSize = resultArr.length;
            if (numOfRecords < 1){
                if(((random.nextInt() < 1 - numOfErrors) ? 0 : 1 ) == 1){
                    int index = random.nextInt(resultArrSize);
                    resultArr[index] = errorGen(resultArr[index]);
                }
            }else{
                for(int k = 0 ; k < numOfErrors; k++){
                    int index = random.nextInt(resultArrSize);
                    resultArr[index] = errorGen(resultArr[index]);
                }
            }
            cleanString = resultArr[0] + " " + resultArr[1] + " " + resultArr[2] + "; " + resultArr[3] + ", ";
            cleanString += resultArr[4] + ", " + resultArr[5] + " " + resultArr[6] + ", " + resultArr[7] + " ";
            cleanString += resultArr[8] + ", " + resultArr[9] + " " + resultArr[10] + " " + resultArr[11] + " " + resultArr[12] + "; " + resultArr[13];

            System.out.println(cleanString);
        }
    }

    private void genData(){
        if(random.nextBoolean()){
            resultArr[0] = curDataArr[2][random.nextInt(sizeArr[2])];
            resultArr[1] = curDataArr[0][random.nextInt(sizeArr[0])];
            resultArr[2] = curDataArr[1][random.nextInt(sizeArr[1])];
        }else{
            resultArr[0] = curDataArr[5][random.nextInt(sizeArr[5])];
            resultArr[1] = curDataArr[3][random.nextInt(sizeArr[3])];
            resultArr[2] = curDataArr[4][random.nextInt(sizeArr[4])];
        }
        resultArr[3] = postcodeGen();
        resultArr[4] = curDataArr[13][4];
        if (random.nextBoolean()){
            resultArr[5] = curDataArr[11][2];
            resultArr[6] = curDataArr[8][random.nextInt(sizeArr[8])];
        }else {
            resultArr[5] = curDataArr[11][random.nextInt(2)];
            resultArr[6] = curDataArr[9][random.nextInt(sizeArr[9])];
        }
        resultArr[7] = curDataArr[13][0];
        resultArr[8] = curDataArr[7][random.nextInt(sizeArr[7])];
        resultArr[9] = curDataArr[13][1];
        resultArr[10] = String.valueOf(random.nextInt(100));
        resultArr[11] = curDataArr[13][2];
        resultArr[12] = String.valueOf(random.nextInt(100));
        resultArr[13] = phoneGen();
    }

    private boolean tryParseSArg(String arg){
        try {
            Integer.parseInt(arg);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean tryParseTArg(String arg){
        try {
            Float.parseFloat(arg);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private String postcodeGen(){
        return curDataArr[12][random.nextInt(sizeArr[12])] + ((int)(Math.random()*(11111+1)) + 11111);
    }

    private String phoneGen(){
        return curDataArr[13][3] + " (" + curDataArr[10][random.nextInt(sizeArr[10])] + ") " + ((int)(Math.random()*(999+1)) + 10) + "-" + ((int)(Math.random()*(99+1)) + 10) + "-" + ((int)(Math.random()*(99+1)) + 10);
    }

    private String errorGen(String record){
        if (record.length()==1) return record;
        if(random.nextBoolean()){
            if (random.nextBoolean())return addCharAt(record);
            else return removeCharAt(record);
        }else return replaceSymbols(record);
    }

    private String removeCharAt(String s) {
        int pos = random.nextInt(s.length());
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    private String addCharAt(String s) {
        String iS = curDataArr[14][random.nextInt(sizeArr[14])];
        int pos = random.nextInt(s.length());
        if(random.nextBoolean()) iS = iS.toLowerCase();
        else iS = String.valueOf(random.nextInt(9));
        if (iS.isEmpty()) return addCharAt(s);
        return s.substring(0, pos+1) + iS + s.substring(pos + 1);
    }

    private String replaceSymbols(String s){
        int lIdx = random.nextInt(s.length()-1);
        int rIdx = lIdx + 1;
        StringBuilder sb = new StringBuilder(s);
        char l = sb.charAt(lIdx), r = sb.charAt(rIdx);
        sb.setCharAt(lIdx, r);
        sb.setCharAt(rIdx, l);
        return sb.toString();
    }
    }
