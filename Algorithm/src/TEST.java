import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import pairs.IPair;
import pairgenerator.PairComboGenerator;


public class TEST {

	private static IPair pairEngine;
	private static int[] sourceStep3 = new int[]{1,2,3,5,8,13,21,34,55,89,144,233,377,610,987,1597,2584,4181,6765,10946,17711};
	
	public static void main(String[] args) throws IOException {
		
		String[] x = new String[]{"AAAA","ABAB","ABBA","AABB","BBBB","BABA","BAAB","BBAA"};
		String[] y = new String[]{"AAAB","AABA","ABAA","BAAA","BBBA","BBAB","BABB","ABBB"};
		
		String rule = "A,A;B,B|AA,BB;AB,BA|AAA,ABA;AAA,BBB;AAA,BAB;ABA,BBB;ABA,BAB;BBB,BAB;AAB,BAA;AAB,ABB;AAB,BBA;BAA,ABB;BAA,BBA;ABB,BBA|";
		
		String xx = "";
		for(int i=0; i<x.length-1; i++)
			for(int j=i+1; j<x.length; j++){
				xx+=x[i]+","+x[j]+";";
			}
		rule += xx;
		
		xx = "";
		for(int i=0; i<y.length-1; i++)
			for(int j=i+1; j<y.length; j++){
				xx+=y[i]+","+y[j]+";";
			}
		xx = xx.substring(0, xx.length()-1);
		rule += xx;
		
		pairEngine = new PairComboGenerator().generate(rule);
		
		
		String file = args[0];
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");//考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while((lineTxt = bufferedReader.readLine()) != null){
            //System.out.println(lineTxt);
            
            String strSource = lineTxt;
    		int length = strSource.length();
    		char[] arrSource = strSource.toCharArray();
    		
    		int header = step1(arrSource);
    		//System.out.println("size : " + header);
    		boolean[] result = step2(strSource, header);
    		
    		for(int i=0; i<header+1; i++)
    			System.out.print('-');
    		for(int i=header+1; i<result.length; i++){
    			if(result[i])
    				System.out.print('o');
    			else
    				System.out.print('x');
    		}System.out.println();
    		System.out.println(strSource);
    		
    		step3(result, header);
        }
        read.close();
        
	}
	
	public static boolean pair(String first, String second){
		return pairEngine.pair(first, second);
	}
	
	public static void step3(boolean[] source, int length){
		int sum = 0;
		int indexSourceStep3 = 0;
		for(int indexSource=length+1; indexSource<source.length; indexSource++){
			if(source[indexSource]){
				sum += sourceStep3[indexSourceStep3];
				System.out.print("+" + sourceStep3[indexSourceStep3]);
				if(indexSourceStep3 != 0)
					indexSourceStep3 -= 1;
			} else {
				sum -= sourceStep3[indexSourceStep3];
				System.out.print("-" + sourceStep3[indexSourceStep3]);
				indexSourceStep3 += 1;
			}
		}
		System.out.println(" = " + sum);
	}
	
	public static boolean[] step2(String source, int length){
		boolean[] rtn = new boolean[source.length()];
		int index = 1;
		while(true){
			
			int i = 0;
			boolean isPair = false;
			
			for( ; i < 4 && !isPair && index+length+(i+1)<source.length(); i++){
				String first = source.substring(index, index + (i+1));
				String second = source.substring(index + length, index + length + (i+1));
				isPair = pair(first, second);
				rtn[index+length+i] = isPair;
			}
			if(i==0)
				break;
			index += i;
		}
		
		return rtn;
	}
	
	public static int step1(char[] array){
		
		int rtn = 1;
		int column = 0;int COLUMN = 4;
		char last = array[0]; //System.out.print(last);
		for(int index=1; index < array.length && column < COLUMN; index++){
			
			if(array[index] != last){
				last = array[index];
				column ++;
				//System.out.println();
			}

			if(column <= COLUMN-1)
				rtn ++ ;
			//System.out.print(array[index]);
		}
		return rtn;
	}

}
