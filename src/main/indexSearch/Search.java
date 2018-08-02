package src.main.indexSearch;
//This class get information from index.txt return the result.
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import src.main.pattern.AdvancedObserver;
import src.main.pattern.Interpretable;
import src.main.pattern.Subject;
import src.main.searchWord.*;
import src.main.simpleSearch.*;
/**
 * 
 * @author dxz
 *
 */
public class Search {
	//The path of the index
	private String directory;
	//Keep the final answer
	private String result;
	//Keep the content corresponding to the index
	private Stack<String> item = new Stack<String>();
	//Keep the operations in sequence
	private Stack<String> op = new Stack<String>();
	//Keep the necessary information of all the file
	private String[] fileInfo;
	
	Subject sub;
	Interpretable sews;
	Interpretable siws;
	SimpleSearch fns;
	SimpleSearch ds;
	SimpleSearch ss;
	
	//Create a default object is forbidden
	@SuppressWarnings("unused")
	private Search(){}
	
	public Search(String indexDir, String s){
		if(!(s == null || s.isEmpty())){
			setDirectory(indexDir);
			prepare(s);
			result = Query(s);
			getInformation(indexDir);
		}
	}
	//Update the file information when the query is finished
	private void getInformation(String indexDir){
		File f = new File(indexDir + File.separator + "FileInformation.txt");
		FileInputStream fin = null; 
        InputStreamReader isr = null;
        BufferedReader br = null;
        try{
        	fin = new FileInputStream(f);
			isr  = new InputStreamReader(fin);
			br = new BufferedReader(isr);
			fileInfo = br.readLine().trim().split(" ");
        	br.close();
        }
        catch(IOException e){e.printStackTrace();}
	}
	/**
	 * Get the index directory
	 */
	public String getDirectory(){
		return directory;
	}
	/**
	 * Set the index directory
	 * @param directory which dir to be searched
	 */
	public void setDirectory(String directory){
		this.directory = directory;
	}
	/**
	 * Return the search result as a string
	 */
	public String getResult(){
		return result;
	}
	/**
	 * Return the search result as a string array
	 */
	public String[] getResults(){
		if(result != null && !result.isEmpty()){
			String [] results = result.split(" ");
			for(int i = 0; i < fileInfo.length; i++){
				for(int j = 0; j < results.length; j++)
					if(fileInfo[i].startsWith(results[j]))
						results[j] = fileInfo[i]; 
			}
			return results;
		}
		else
			return null;
	}
	//Pre-analyze the query
	private void prepare(String s){
		StringTokenizer st = new StringTokenizer(s, " ()");
		String temp = "";
		while(true){
			temp = st.nextToken();
			if(temp.startsWith("fileName:")){
				//If the type is "file name".
				if(fns == null)
					//Create a object of FileNameSearch
					fns = new FileNameSearch();
				//Add the contents into the object
				fns.add(temp.substring(temp.indexOf(':') + 1));
			}
			else if(temp.startsWith("\"")){
				//If the type is "proximity"
				while(!temp.endsWith("\"") && !temp.contains("~"))
				//Get all the part of a proximity query
						temp += " " + st.nextToken();
				if(sub == null)
					//Create a object of WordIndexLoader
					//WordIndexLoader will be a subject of SerialWordSearch and SingleWordSearch
					sub = new WordIndexLoader(directory);
				if(sews == null){
					//Create a object of SerialWordSearch
					sews = new SerialWordSearch();
                    //SerialWordSearch is an observer of WordIndexLoader
					sub.registerObserver((AdvancedObserver)sews);
				}
				sews.add(temp);
			}
			else if(temp.startsWith("fileSize:")){
				//If the type is "file size"
				if(ss == null)
					//Create a object of SizeSearch
					ss = new SizeSearch();
				ss.add(temp.substring(temp.indexOf(':') + 1));
			}
			else if(temp.startsWith("modTime:")){
				//If the type is "file modify time"
				if(ds == null)
					ds = new DateSearch();
				ds.add(temp.substring(temp.indexOf(':') + 1));
			}
			else if(temp != null && !temp.isEmpty() && !(temp.equals("AND") || temp.equals("OR") || temp.equals("NOT") || temp.equals("(") || temp.equals(")"))){
                //If the type is "file contents"
				if(temp.startsWith("fileContents:"))
					temp = temp.substring(temp.indexOf(':') + 1);
				if(sub == null)
					sub = new WordIndexLoader(directory);
				if(siws == null){
					//Create a object of SingleWordSearch
					siws = new SingleWordSearch();
					//SingleWordSearch is an observer of WordIndexLoader
					sub.registerObserver((AdvancedObserver)siws);
				}
				siws.add(temp);
			}
			if(!st.hasMoreTokens())
				break;
		}
		getContents();
	}
	//Get contents from Index
	private void getContents(){
		if(sub != null){
			while(!sub.canStop())
				sub.notifyObserver();
			sub.stop();
		}
		if(fns != null){
			fns.update(directory, "FileNamesIndex.txt");
		}
		if(ds != null){
			ds.update(directory, "FileDateIndex.txt");
		}
		if(ss != null){
			ss.update(directory, "FileSizeIndex.txt");
		}
	}
    //Analyze the sentence for query and get the answer
	private String Query(String s){
		StringTokenizer token = new StringTokenizer(s, " ()", true);
		String word = "";
		
		while(true){
			word = token.nextToken();
			if(word == null)
				break;
			if(word.equals("AND") || word.equals("OR")){
				if(!op.isEmpty() && !op.peek().equals("("))
					compute();
				op.push(word);
			}
			else if(word.equals("(") || word.equals("NOT"))
				op.push(word);
			else if(word.equals(")")){
				if(!op.peek().equals("("))
					compute();
				op.pop();
			}
			else{
				//Create a reference of current object to give its contents
				Interpretable inter = null;
				if(word.startsWith("fileName:")){
					inter = fns;
					item.add(inter.getContents());
				}
				else if(word.startsWith("\"")){
					while(!word.endsWith("\"") && !word.contains("~"))
						word += token.nextToken();
					inter = sews;
					item.add(inter.getContents());
				}
				else if(word.startsWith("fileSize:")){
					inter = ss;
					item.add(inter.getContents());
				}
				else if(word.startsWith("modTime:")){
					inter = ds;
					item.add(inter.getContents());
				}
				else if(word.startsWith("fileContents:")){
					inter = siws;
					item.add(inter.getContents());
				}
				else if(word != null && word.compareTo("0") >= 0){
					inter = siws;
					item.add(inter.getContents());
				}
			}
			if(!token.hasMoreTokens()){
				break;
			}
		}
		while(!op.isEmpty())
			compute();
		return item.pop();
	}
	//Do the set operation
	private void compute(){
		String operator = (String)op.pop();
		String op1 = (String)item.pop();
		String op2 = null;
		if(operator.equals("AND")){
			op2 = item.pop();
			item.push(Operation.And(op1, op2));
		}
		else if(operator.equals("OR")){
			op2 = item.pop();
			item.push(Operation.Or(op1, op2));
		}
		else if(operator.equals("NOT"))
			item.push(Operation.Not(op1));
		else
			item.push(op1);
	}
}