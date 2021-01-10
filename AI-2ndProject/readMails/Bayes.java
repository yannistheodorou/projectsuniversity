import java.util.*;

public class Bayes{
	//private HashSet<String> knownAttributes = new HashSet<String>();
	private ArrayList<Message> trainingSet = new ArrayList<Message>();
	private HashSet<Attribute> attributeList = new HashSet<Attribute>();
	private byte spamNumber = 0;

	public void calcPropabilities(){
		System.out.println("Calculating propabilities for "+attributeList.size()+" attributes.");
		for(Attribute att : attributeList){
			int presentSPAMcount =0 , presentHAMcount = 0;
			for(Message m : trainingSet){
				HashSet<String> mAtts = m.calcAttributes();
				if(m.isSpam()){
					if(mAtts.contains(att.getWord()))
						presentSPAMcount++;
				}else{
					if(mAtts.contains(att.getWord()))
						presentHAMcount++;
				}
			}
			att.setPropPresentSpam(presentSPAMcount +1d/ spamNumber+2);
			att.setPropAbsentSpam(1 - att.getPropPresentSpam());
			att.setPropPresentHam(presentHAMcount +1d/ (trainingSet.size() - spamNumber+2));
			att.setPropAbsentHam(1 - att.getPropPresentHam());
			//att.printPropabilities();
		}
	}
	
	private static double xlog2x(double x){
		return 1d*x*(Math.log(x)/Math.log(2));
		}
	
	public void calcIG(){
		System.out.println("Calculating IGs for "+attributeList.size()+" attributes.");
		double Hc = -xlog2x(1d*spamNumber/trainingSet.size())-xlog2x(1d*(trainingSet.size()-spamNumber)/trainingSet.size());
		for(Attribute att : attributeList){
			int presentInSpam = 0, presentInHam = 0;
			for(Message m : trainingSet){
				if(m.getAttributes().contains(att.getWord())){
					if(m.isSpam())
						presentInSpam++;
					else
						presentInHam++;
				}
			}
			att.setIG(Hc-(1d*(presentInHam+presentInSpam)/trainingSet.size())*(
						-xlog2x((presentInSpam)/1d*(presentInSpam+presentInHam))
						-xlog2x((presentInHam)/1d*(presentInSpam+presentInHam))
						)
					-((1d*(presentInHam+presentInSpam)-trainingSet.size())/trainingSet.size())*(
						-xlog2x((spamNumber)/(trainingSet.size()-presentInSpam-presentInHam))
						-xlog2x((trainingSet.size()-spamNumber)/(trainingSet.size()-presentInSpam-presentInHam))
						
						)
					);
		}
	}
	//Keeps the best m Attributes according to their IG score
	public void calcBestAttributes(int m){
		calcIG();
		ArrayList<Attribute> temp = new ArrayList<Attribute>();
		for(Attribute att : attributeList){
			temp.add(att);
			System.out.println(""+att.getIG());
		}
		temp.sort(null);
		attributeList = new HashSet<Attribute>();
		for(int i=0 ; m>i ; i++){
			if(temp.size()<=0)break;
			attributeList.add(temp.remove(0));
		}
	}

	public Bayes(){

	}

	public ArrayList<Message> getTrainingSet(){
		return trainingSet;
	}

	public void addTrainingExamples(ArrayList<Message> set){
		System.out.println("Processing training set.");
		int i=0;
		for(Message m : set){
			i++;
			System.out.println("Processing message:"+i);
			trainingSet.add(m);
			if(m.isSpam()) spamNumber++;
			for(String att : m.getAttributes()){
				//knownAttributes.add(att);
				attributeList.add(new Attribute(att));
			}
		}
		this.calcPropabilities();
	}
	public void addTrainingExample(Message m){
		trainingSet.add(m);
		if(m.isSpam()) spamNumber++;
		for(String att : m.getAttributes()){
			//knownAttributes.add(att);
			attributeList.add(new Attribute(att));
		}
		this.calcPropabilities();//if the training set changes, propability tables must be recalculated.
	}

	//This function returns true if the Algorithms would categorize the Message m as spam.
	public boolean mustbeSpam(Message m){
		double pSpam = ((spamNumber + 0.0d) / (trainingSet.size() + 0.0d));
		double pHam = 1d - pSpam;

		HashSet<String> messageAttributes = m.getAttributes();
		for(Attribute att : attributeList){
			if(messageAttributes.contains(att.getWord())){
				//calc TRUE//PRESENT
				pSpam = pSpam * att.getPropPresentSpam();
				pHam = pHam * att.getPropPresentHam();
			}else{
				//calc FALSE//ABSENT
				pSpam = pSpam * att.getPropAbsentSpam();
				pHam = pHam * att.getPropAbsentHam();
			}
		}
		return pSpam > pHam;
	}
}
