package ma.org.stack;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LogicToXml {

	static final String OR = "OR";
	static final String AND = "AND";
	static final String NOT = "NOT";

	static final String XML_CONDICATION = "CONDITION";
	static final String XML_ITEM = "ITEM";
	static final String XML_key = "key";
	static final String XML_val = "val";
	static final String XML_rel = "rel";

	public static final String pair_left = "(";
	public static final String pair_right = ")";
	
	private static Map<String,String> relMap = new HashMap<String,String>();
	
	static{
		relMap.put(AND, AND);
		relMap.put(OR, OR);
		relMap.put(NOT, NOT);
	}

	
	public static void ConvertLogicBySpace(String str){
		ConvertLogic(str," ");
	}
	
	public static void ConvertLogic(String str,String split){
		String [] ss = str.split(split);
		List<String> logicSrcList = new ArrayList<String>();
		for(String s : ss){
			s = s.trim();
			if(!"".equals(s) ){
				logicSrcList.add(s);
			}
		}
		//1 整理( )
		pairing(logicSrcList);
		printList(logicSrcList);
		
		//3 去掉无用的括号
		delPairing(logicSrcList);
		printList(logicSrcList);
		
		
		//2 在合适的位置添加括号f
		addPairing(logicSrcList);
		
		printList(logicSrcList);
		
		
		
		//3 生成LogicCondition
	//	return LogicCondition.CreateByList(logicSrcList);
		
	}


	/**
	 * 整理括号，并且在最前面和最后面强制增加一个
	 * @param logicSrcList
	 * @return
	 */
	private static void pairing(List<String> logicSrcList) {
		int pairCount = 0;
		for(int i = 0 ; i< logicSrcList.size(); i++){
			String mark = logicSrcList.get(i);
			if(pair_left.equals(mark)){
				pairCount ++;
			}else if(pair_right.equals(mark)){
				if(pairCount == 0){
					logicSrcList.remove(i);
					i--;
				}else{
					pairCount--;
				}
				
			}
		}
		if(pairCount > 0){
			for(int i = 0 ; i< pairCount ; i++){
				logicSrcList.add(pair_right);
			}
		}
		
		String s = logicSrcList.get(0);
		String e = logicSrcList.get(logicSrcList.size() -1);
		if(!pair_left.equals(s) || !pair_right.equals(e)){
			logicSrcList.add(pair_right);
			logicSrcList.add(0,pair_left);
		}
	}

	/**
	 *  在合适的位置添加括号f
	 * @param logicSrcList
	 */
	private static void addPairing(List<String> logicSrcList) {
		LogicStack<Integer> pairingIndex = new LogicStack<Integer>();
		LogicStack<String> pairingMark = new LogicStack<String>();
		String tempMark = null;  // OR \AND \NOT
		for(int i = 0 ; i< logicSrcList.size(); i++){
			String mark = logicSrcList.get(i);
			if(pair_left.equals(mark)){
				pairingIndex.push(i);
				if(tempMark != null){
					pairingMark.push(tempMark);
				}
				tempMark = null;
				
			}else if(relMap.containsKey(mark)){
				if(tempMark == null){
					tempMark = mark;
				}else if(!mark.equals(tempMark)){
					Integer top = pairingIndex.top();
					if(top == null){
						System.out.println(i);
						top = 0;
					}
					logicSrcList.add(i,pair_right);
					logicSrcList.add(top,pair_left);
					i = i + 2;
					tempMark = mark;
				}else{
					
				}
			}else if(pair_right.equals(mark)){
				pairingIndex.pop();
				tempMark = pairingMark.pop();
			}
		}
	}

	/**
	 * 去掉无用的括号
	 * @param logicSrcList
	 */
	private static void delPairing(List<String> logicSrcList) {
		LogicStack<DelPairParam> pairingIndex = new LogicStack<DelPairParam>();
		for(int i = 0 ; i< logicSrcList.size(); i++){
			String mark = logicSrcList.get(i);
			if(pair_left.equals(mark)){
				DelPairParam param = new DelPairParam(i,0);
				pairingIndex.push(param);
			}else if(pair_right.equals(mark)){
				DelPairParam param =  pairingIndex.top();
				if(param.num > 0){
					
				}else{
					logicSrcList.remove(i);
					logicSrcList.remove(param.index);
					pairingIndex.pop();
					i= i-2;
				}
			}else if(relMap.containsKey(mark)){
				DelPairParam param =  pairingIndex.top();
				param.num = param.num + 1;
			}
		}
	}
	
	
	
	
	public static void printList(List<?> list){
		for(int i = 0 ;i< list.size() ; i++){
			System.out.print(list.get(i) + ",");
		}
		System.out.println("-----------------------");
	}
	
	public static void main(String[] args) {
		 LogicToXml.ConvertLogicBySpace(" ( ( a=a1 ) ) OR ) ) ( b=b1 )  AND ( ( ) ) e=e1 OR f=f1 OR ( g=g1 AND h=h1 ) AND   i=i1 OR J1=J2 ");
	
		 System.out.println("-----------------------");
		 LogicToXml.ConvertLogicBySpace("  ( a=a1 OR ( b=b1   AND ( ( ) ) e=e1 ) ) OR f=f1 OR ( g=g1 AND h=h1 ) AND   i=i1 OR J1=J2 ");
	}
	
	
	
	
	private static class DelPairParam{
		public DelPairParam(int i, int n) {
			index = i;
			num = n;
		}
		public int index;
		public int num;
	}
}

