import java.util.*;

public class Attribute implements Comparable<Attribute>{
	//private static ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
	//private double[][] P = new double[2][2]();
	private double IG;
	private double presentSPAM = 0, absentSPAM = 0, presentHAM = 0, absentHAM = 0;
	private String word;
	public Attribute(String word){
		this.word = word;
		//attributeList.add(this);
	}

	public void setIG(double ig){
		this.IG = ig;
	}

	public double getIG(){
		return IG;
	}

	public int compareTo(Attribute other){
		if(this.IG == other.getIG())return 0;
		return this.IG < other.getIG()?-1:+1;
	}

//	public static void calcPropabilities(){
//		for(Attribute att : attributeList){
//			int presentSPAMcount =0 ,/*= absentSPAMcount */ presentHAMcount /*= absentHAMcount */= 0;
//			for(Message m : trainingSet){
//				HashSet<String> mAtts = m.calcAttributes();
//				if(m.isSpam()){
//					if(mAtts.contains(att.word))
//						presentSPAMcount++;
//				//	else
//				//		absentSPAMcount++;
//				}else{
//					if(mAtts.contains(att.word))
//						presentHAMcount++;
//				//	else
//				//		absentHAMcount++;
//				}
//			}
//			presentSPAM = (presentSPAMcount / spamNumber);
//			absentSPAM = 1 - absentSPAM;
//			presentHAM = (presentHAMcount / (kTrainingSet.size() - spamNumber));//TODO
//			absentHAM = 1 - presentHAM;
//		}
//	}


	public String getWord(){
		return this.word;
	}

	public void printPropabilities(){
		System.out.println("P("+word+"=true|c=SPAM)= "+presentSPAM);
		System.out.println("P("+word+"=false|c=SPAM)= "+absentSPAM);
		System.out.println("P("+word+"=true|c=HAM)= "+presentHAM);
		System.out.println("P("+word+"=false|c=HAM)= "+absentHAM);
	}


	public double getPropPresentSpam(){return presentSPAM;}
	public double getPropAbsentSpam(){return absentSPAM;}
	public double getPropPresentHam(){return presentHAM;}
	public double getPropAbsentHam(){return absentHAM;}

	public void setPropPresentSpam(double p){presentSPAM = p;}
	public void setPropAbsentSpam(double p){absentSPAM = p;}
	public void setPropPresentHam(double p){presentHAM = p;}
	public void setPropAbsentHam(double p){absentHAM = p;}
}
