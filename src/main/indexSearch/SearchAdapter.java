package src.main.indexSearch;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import src.main.Tester.Result;
/**
 * 
 * @author dxz
 *This class is an adapter to meet the requirements of tester
 */
public class SearchAdapter {
	private SearchAdapter(){}
	
	public static List<Result> getList(File indexDir, String query, int order){
		Operation.getList(indexDir.getAbsolutePath());
		Search s = new Search(indexDir.getAbsolutePath(), query);
		String[] results = s.getResults();
		if(results != null){
			ArrayList<Result> al = new ArrayList<Result>();
			for(int i = 0; i < results.length; i++){
				Result r = new Result();
				String[] rs = results[i].split(",");
				r.fileName = rs[0];
				r.modTime = rs[1];
				r.fileSize = Integer.parseInt(rs[2]);
				if(order == 0)
					al.add(r);
				else if(order == 1){
					if(al.size() == 0)
						al.add(r);
					else{
						int j = 0;
						for(; j < al.size(); j++)
							if(al.get(j).fileSize <= r.fileSize){
								al.add(j, r);
								break;
							}
						if(j == al.size())
							al.add(r);
					}
				}
				else if(order == 2){
					if(al.size() == 0)
						al.add(r);
					else{
						int j = 0;
						for(; j < al.size(); j++)
							if(al.get(j).modTime.compareTo(r.modTime) <= 0){
								al.add(j, r);
								break;
							}
						if(j == al.size())
							al.add(r);
					}
				}
			}
			return al;
		}
		return null;
	}
}
