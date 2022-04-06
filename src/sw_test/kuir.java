package sw_test;

public class kuir {

	public static void main(String[] args) throws Exception {
		String command=args[0];
		String path=args[1];
		
//		if(command.equals("-c")) {
//			makeCollection collection = new makeCollection();
//			collection.firstweek();
//		}
//		else if(command.equals("-k")) {
//			makeKeyword keyword=new makeKeyword();
//			keyword.secondweek();
//		}
//		else if(command.equals("-i")) {
//			indexer index=new indexer();
//			index.thirdweek();
//		}
		indexer index=new indexer();
		index.thirdweek();
	}

}
