package sw_test;

public class kuir {

	public static void main(String[] args) throws Exception {
		String command=args[0];
		String path=args[1];

		if(command.equals("-c")) {
			makeCollection collection = new makeCollection(path);
			collection.firstweek();
		}
		else if(command.equals("-k")) {
			String xmlRoute="./SimpleIR"+path;
			makeKeyword keyword=new makeKeyword(xmlRoute);
			keyword.secondweek();
		}
		else if(command.equals("-i")) {
			String xmlRoute="./SimpleIR"+path;
			indexer index=new indexer(xmlRoute);
			index.thirdweek();
		}
		else if(command.equals("-s")) {
			String postRoute="./SimpleIR"+path;
			if(args[2].equals("-q")){
				String query=args[3];
				searcher search=new searcher(postRoute, query);
				search.forthweek();
			}
			else {
				System.out.println("쿼리가 존재하지 않습니다.");
			}
		}
		else if(command.equals("-m")) {
			String cRoute="./SimpleIR/collection.xml";
			if(args[2].equals("-q"));{
				String query=args[3];
				MidTerm mid=new MidTerm(cRoute, query);
				mid.showSnippet();
			}
		}
	}

}
