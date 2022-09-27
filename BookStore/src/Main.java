import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.border.Border;


public class Main extends JFrame{
	
	JPanel P1 = new JPanel(new FlowLayout()); 
	JPanel P2 = new JPanel(new FlowLayout()); 

	JLabel AmountLabel = new JLabel("판 매 액"); 
	
	JTextField AmountPrint = new JTextField(18);
	JTextField NameList = new JTextField();
	
	JButton Input = new JButton("저장");
	JButton Delete = new JButton("삭제");
	JButton Sale = new JButton("판매");
	JButton Search = new JButton("검색");
	JButton AmountControl = new JButton("판매액 조정");

	InputDialog Input_D;
	DeleteDialog Delete_D;
	SaleDialog Sale_D;
	AmountDialog Amount_D;
	SearchDialog Search_D;
	
	Vector<String> Name = new Vector<String>();
	Vector<String> Date = new Vector<String>();
	Vector<String> Author = new Vector<String>();
	Vector<String> Publisher = new Vector<String>();
	Vector<String> Price = new Vector<String>();
	Vector<String> Amount = new Vector<String>();
	Vector<String> Content = new Vector<String>();
	
	JList<String> List = new JList<String>(Name);
	JList<String> Receipt = new JList<String>(Amount);
	
	int AmountCount = 0;
	String AmountCountPrint = String.valueOf(AmountCount);
	
public Main(){	
	setTitle("도서 판매 프로그램");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	AmountPrint.setText(AmountCountPrint);
	
		CreateMenu();
		add("North",P1);
		P1.add(Input);
		P1.add(Delete);
		P1.add(Sale);
		P1.add(Search);
		
		add("Center",NameList);
		add(new JScrollPane(List));
		
		add("South",P2);
		P2.add(AmountLabel);
		P2.add(AmountPrint);
		P2.add(AmountControl);
		
		
		Input_D = new InputDialog(this, "저장");
		Input.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Input_D.setResizable(false);
				Input_D.setVisible(true);
			}
		});
		
		Delete_D = new DeleteDialog(this, "삭제");
		Delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Delete_D.setResizable(false);
				Delete_D.setVisible(true);
			}
		});
		
		Sale_D = new SaleDialog(this, "판매");
		Sale.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Sale_D.setResizable(false);
				Sale_D.setVisible(true);
			}
		});
		
		Amount_D = new AmountDialog(this, "판매액 조정");
		AmountControl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Amount_D.setResizable(false);
				Amount_D.setVisible(true);
			}
		});
		
		Search_D = new SearchDialog(this, "도서 검색");
		Search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Search_D.setResizable(false);
				Search_D.setVisible(true);
			}
		});
		
		AmountLabel.setHorizontalAlignment(JLabel.LEFT);
		AmountPrint.setEditable(false);
		
		setResizable(false);
		setSize(380,250);
		setVisible(true);
	}

public void CreateMenu() {

		JMenuBar Menu = new JMenuBar();
		JMenuItem [] MenuItem = new JMenuItem[2];
		String[] ItemTitle = {"도움말", "초기화"};
		JMenu ScreenMenu = new JMenu("설정");
		MenuActionListener listener = new MenuActionListener();
		
		for(int i=0;i<MenuItem.length;i++) {
			MenuItem[i] = new JMenuItem(ItemTitle[i]);
			MenuItem[i].addActionListener(listener);
			ScreenMenu.add(MenuItem[i]);
		}
		
		Menu.add(ScreenMenu);
		setJMenuBar(Menu);
	}
class MenuActionListener implements ActionListener{
	
	String InformationText = "이 프로그램은 온라인 도서 쇼핑몰입니다."
			+ "\n판매할 수 있는 도서의 재고는 계속 공급되고 있습니다."
			+ "\n각각의 기능을 통해서 도서의 판매 및 관리를 할 수 있습니다."
			+ "\n초기화를 누르시면 모든 내용을 초기화 할 수 있습니다.";
	
	public void actionPerformed(ActionEvent e) {
		String Choice = e.getActionCommand();
		switch(Choice) {
		case "초기화" :
			Name.clear();
			Date.clear();
			Author.clear();
			Publisher.clear();
			Price.clear();
			Amount.clear();
			Content.clear();
			
			AmountCount = 0;
			AmountCountPrint = String.valueOf(AmountCount);
			AmountPrint.setText(AmountCountPrint);
			JOptionPane.showMessageDialog(null,"초기화가 되었습니다.","안내!",JOptionPane.INFORMATION_MESSAGE);
			break;
			
		case "도움말" :
			JOptionPane.showMessageDialog(null,InformationText,"안내!",JOptionPane.INFORMATION_MESSAGE);
		}
	}
};

class InputDialog extends JDialog {

	JTextField NameInput = new JTextField(15);
	JTextField DateInput = new JTextField(15);
	JTextField AuthorInput = new JTextField(15);
	JTextField PublisherInput = new JTextField(15);
	JTextField PriceInput = new JTextField(15);
	JTextArea BookContent = new JTextArea(7,18);
	
	JLabel NameLabel = new JLabel("이름");
	JLabel DateLabel = new JLabel("날짜");
	JLabel AuthorLabel = new JLabel("작가");
	JLabel PublisherLabel = new JLabel("출판");
	JLabel PriceLabel = new JLabel("가격");
	JLabel ContentLabel = new JLabel("도서 설명");
	
	JTextArea Information = new JTextArea();
	JButton Confirm = new JButton("확인");
	JButton Exit = new JButton("종료");
	
	Border lineBorder = BorderFactory.createLineBorder(Color.black, 1);
	 
	  	/*입력받는 날짜를 구현하는 방법. 
	  	2021년 06월 21일을 입력을 받는다면 2021 06 21을 입력받는다.
	  	문자열로 변환해서 검사한다. 구현 방식은 아래와 같다.
	  	
	  	String S;
	  	String intStr = S.replaceAll([^d], );
	  	System.out.println(intStr);
	  
	   위에서 검사한 문자열의 길이가 8이 아니라면 오류.
	  	안내문에는 09, 08, 07, 06…과 같은 형식으로 표기하도록.
	  	길이가 8이라면 해당 문자열을 substring을 통해서 XXXX년, XX월, XX일의 형식으로 분리.
	  	해당하는 문자들을 수정해서 하나의 문자열로 만들어서 벡터에 저장.*/
	 
	
	String InputName;
	String InputDate;
	String InputAuthor;
	String InputPublisher;
	String InputPrice;
	String InputContent;
	String Str;
	int Num = 0;
	
	JPanel IN1 = new JPanel(new FlowLayout());
	JPanel IN2 = new JPanel(new FlowLayout());
	JPanel IN3 = new JPanel(new FlowLayout());
	
	public InputDialog(JFrame frame, String title){
		super(frame,title);
		add("North", IN3);
		IN3.add(Information);
		Information.setText("도서의 정보를 작성해주세요."
							+ "\n날짜는 출판날짜입니다."
							+ "\n출판은 출판사입니다."
							+ "\n"
							+ "\n※주의사항"
							+ "\n같은 이름의 책은 추가할 수 없습니다."
							+ "\n날짜는 8개의 정수를 입력받습니다."
							+ "\n예시 2021 12 11  20210402"
							+ "\n가격은 정수를 입력받습니다.");
		
		Information.setBorder(BorderFactory.createCompoundBorder(lineBorder,null));
		
		add("Center",IN1);
		IN1.add(NameLabel);
		IN1.add(NameInput);
		IN1.add(DateLabel);
		IN1.add(DateInput);
		IN1.add(AuthorLabel);
		IN1.add(AuthorInput);
		IN1.add(PublisherLabel);
		IN1.add(PublisherInput);
		IN1.add(PriceLabel);
		IN1.add(PriceInput);
		IN1.add(ContentLabel);
		IN1.add(BookContent);
		IN1.add(new JScrollPane(BookContent));
		
		BookContent.setLineWrap(true);
		BookContent.setBorder(BorderFactory.createCompoundBorder(lineBorder,null));
		
		
		add("South",IN2);
		IN2.add(Confirm);
		IN2.add(Exit);
		
		setSize(220,550);
		Information.setEditable(false);
		
		
		Confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				Num = 0;
				InputName = String.valueOf(NameInput.getText());
				InputDate = String.valueOf(DateInput.getText());
				InputAuthor = String.valueOf(AuthorInput.getText());
				InputPublisher = String.valueOf(PublisherInput.getText());
				InputPrice = String.valueOf(PriceInput.getText());
				InputContent = String.valueOf(BookContent.getText());
				
				String intInputDate = InputDate.replaceAll("[^\\d]", "");
				String intInputPrice = InputPrice.replaceAll("[^\\d]", "");
				String StringInputDate = InputDate.replaceAll("[0-9]", "");
				String StringInputPrice = InputPrice.replaceAll("[0-9]", "");
				//https://codechacha.com/ko/java-extract-integers-from-string/
				
				if(Name.size()>0) {
					for(int i=0;i<Name.size();i++) {
						if(InputName.equals(Name.get(i))){
							Num = 1;						}
					}
				}
				
				if(Num != 0) {
					JOptionPane.showMessageDialog(null,"이미 존재하는 도서입니다.","오류!",JOptionPane.ERROR_MESSAGE);
				}
				
				else if(NameInput.getText().length() == 0
					|| DateInput.getText().length() == 0 
					|| AuthorInput.getText().length() == 0 
					|| PublisherInput.getText().length() == 0	
					|| PriceInput.getText().length() == 0
					|| BookContent.getText().length() == 0) {
					
					JOptionPane.showMessageDialog(null,"모든 항목을 입력해주세요.","오류!",JOptionPane.ERROR_MESSAGE);
				}
				
				else if((intInputDate.length() != 8 || StringInputDate.length() != 0) && (intInputPrice.length() == 0 || StringInputPrice.length() != 0)) {
					JOptionPane.showMessageDialog(null,"날짜와 가격이 둘 다 잘못됐습니다. 다시 입력해주세요.","오류!",JOptionPane.ERROR_MESSAGE);
				}
				
				else if(intInputDate.length() != 8 || StringInputDate.length() != 0) {
					JOptionPane.showMessageDialog(null,"날짜가 입력이 잘못됐습니다. 다시 입력해주세요.","오류!",JOptionPane.ERROR_MESSAGE);
				}
				
				else if(intInputPrice.length() == 0 || StringInputPrice.length() != 0) {
					JOptionPane.showMessageDialog(null,"가격이 입력이 잘못됐습니다. 다시 입력해주세요.","오류!",JOptionPane.ERROR_MESSAGE);
				}
								
				
				else{
				Name.add(InputName);
				Date.add(intInputDate);
				Author.add(InputAuthor);
				Publisher.add(InputPublisher);
				Price.add(intInputPrice);
				Content.add(InputContent);
				}
							
				List.setListData(Name);	
				NameInput.setText("");
				DateInput.setText("");
				AuthorInput.setText("");
				PublisherInput.setText("");
				PriceInput.setText("");
				BookContent.setText("");
			}	
		});
		
		Exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				NameInput.setText("");
				DateInput.setText("");
				AuthorInput.setText("");
				PublisherInput.setText("");
				PriceInput.setText("");
				BookContent.setText("");
				}
			});
		
	}
}
class DeleteDialog extends JDialog {
	JPanel DE1 = new JPanel(new FlowLayout());
	JPanel DE2 = new JPanel(new FlowLayout());
	
	JTextArea Information = new JTextArea();
	JButton Confirm = new JButton("확인");
	JButton Exit = new JButton("종료");
	
	JLabel NameLabel = new JLabel("이름");
	JTextField NameInput = new JTextField(20);
	String DeleteName;
	int Num = -1;
	
	public DeleteDialog(JFrame frame, String title) {
		super(frame,title);
		add("Center",DE1);
		DE1.add(NameLabel);
		DE1.add(NameInput);
		setSize(280,110);
		add("South",DE2);
		DE2.add(Confirm);
		DE2.add(Exit);
		
		Confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DeleteName = String.valueOf(NameInput.getText());
				
				for(int i=0;i<Name.size();i++) {
					if(DeleteName.equals(Name.get(i))){
						Num = i;
						break;
					}
				}
				
				if(Num != -1) {
				Name.remove(Num);
				Date.remove(Num);
				Author.remove(Num);
				Publisher.remove(Num);
				Price.remove(Num);
				Content.remove(Num);
				Num = -1;
				}
				
				else {
					JOptionPane.showMessageDialog(null,"도서를 찾을 수 없습니다.","오류!",JOptionPane.ERROR_MESSAGE);
				}

				List.setListData(Name);
				NameInput.setText("");
			}
		});
		
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				NameInput.setText("");
			}
		});
		
	}
}
class SaleDialog extends JDialog {
	JLabel SaleLabel = new JLabel("이름");
	JLabel SaleInformation = new JLabel();
	JLabel Quantity = new JLabel("수량");
	
	JPanel SA1 = new JPanel(new FlowLayout());
	JPanel SA2 = new JPanel(new FlowLayout());
	JPanel SA3 = new JPanel(new FlowLayout());
	
	JTextField AmountList = new JTextField(18);
	JTextField SaleInput = new JTextField(16);
	JTextField QuantityInput = new JTextField(2);
	JTextArea Information = new JTextArea(3,10);
	
	JButton Confirm = new JButton("확인");
	JButton Reset = new JButton("초기화");
	JButton Exit = new JButton("종료");
	
	String SaleName;
	String InformName;
	String InformDate;
	String InformAuthor;
	String InformPublisher;
	String InformPrice;
	String InformQuantity;
	String InputQuantity;
	
	int IntQuantity;
	int Num = -1;
	int Account;
	int AddCount;
	
	public SaleDialog(JFrame frame, String title) {
		super(frame,title);
		
		add("North",SA1);
		SA1.add(SaleLabel);
		SA1.add(SaleInput);
		SA1.add(Quantity);
		SA1.add(QuantityInput);
		
		add("Center",SA2);
		SA2.add(AmountList);
		add(new JScrollPane(Receipt));
		
		add("South",SA3);
		SA3.add(Confirm);
		SA3.add(Exit);
		SA3.add(Reset);
		SA3.add(Information);
		
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1);
		Information.setText("판매하는 수량은 99개까지만 허용됩니다."
				+ "\n판매내역은 다음과 같은 순서로 표시됩니다."
				+ "\n이름/출판사/작가/가격/총액/수량");
		Information.setBorder(BorderFactory.createCompoundBorder(lineBorder,null));
		Confirm.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				SaleName = String.valueOf(SaleInput.getText());
				InputQuantity= String.valueOf(QuantityInput.getText());
				InformQuantity = InputQuantity.replaceAll("[^\\d]", "");
				for(int i=0;i<Name.size();i++) {
					if(SaleName.equals(Name.get(i))){
						Num = i;
						break;
					}
				}
				
				if(Num == -1){
					JOptionPane.showMessageDialog(null,"찾을 수 없는 도서입니다.","오류!",JOptionPane.ERROR_MESSAGE);
				}
				
				if(InformQuantity.length() == 0 || QuantityInput.getText().length() == 0 || InformQuantity.equals("0")) {
					JOptionPane.showMessageDialog(null,"수량을 다시 입력해주세요.","오류!",JOptionPane.ERROR_MESSAGE);
				}
					
				else if(Num != -1) {
				IntQuantity = Integer.valueOf(InformQuantity);
				if(IntQuantity>99) {
					JOptionPane.showMessageDialog(null,"수량을 초과했습니다.","오류!",JOptionPane.ERROR_MESSAGE);
				}
				else {
				InformName = Name.get(Num);
				InformDate = Date.get(Num);
				InformAuthor = Author.get(Num);
				InformPublisher = Publisher.get(Num);
				InformPrice = Price.get(Num);
				
				for(AddCount = 0;AddCount<IntQuantity;AddCount++) {
				Account = Integer.valueOf(InformPrice);
				AmountCount += Account;
				
				AmountCountPrint = String.valueOf(AmountCount);
				AmountPrint.setText(AmountCountPrint);
				}
				
				Amount.add(InformName +" "+ InformPublisher +" "+ InformAuthor  +" "+ InformPrice + " " + AmountCountPrint+ " "+ AddCount + "권 판매");
				Num = -1;
				}
				}
				Receipt.setListData(Amount);
				SaleInput.setText("");
				QuantityInput.setText("");
			}	
		});
		
		Reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Amount.removeAllElements();
				Receipt.setListData(Amount);
				SaleInput.setText("");
				QuantityInput.setText("");
			}
		});
		
		Exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SaleInput.setText("");
				QuantityInput.setText("");
			}
		});
		
		setSize(465,300);
		Information.setEditable(false);
	}
	
	}
class AmountDialog extends JDialog{
	
	JPanel AM1 = new JPanel(new FlowLayout());
	JPanel AM2 = new JPanel(new FlowLayout());
	
	JTextArea Information = new JTextArea();
	JButton Confirm = new JButton("확인");
	JButton Exit = new JButton("종료");
	
	int Account;
	
	JLabel AmountInputLabel = new JLabel("입력");
	JTextField AmountInput = new JTextField(18);
	String AmountString;
	
	public AmountDialog(JFrame frame, String title) {
		super(frame,title);
		add("North",AM1);
		AM1.add(AmountInputLabel);
		AM1.add(AmountInput);
		add("South",AM2);
		AM2.add(Confirm);
		AM2.add(Exit);
		
		setSize(280,110);
		
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AmountInput.setText("");
			}	
		});
		
		Confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AmountString = String.valueOf(AmountInput.getText());
				AmountString = AmountString.replaceAll("[0-9]", "");
				
				if(AmountInput.getText().length() == 0 || AmountString.length() != 0) {
					JOptionPane.showMessageDialog(null,"판매액이 잘못 입력됐습니다..","오류!",JOptionPane.ERROR_MESSAGE);
				}
				
				else {
				Account = Integer.valueOf(AmountInput.getText());
				AmountCount = Account;
				
				AmountCountPrint = String.valueOf(AmountCount);
				AmountPrint.setText(AmountCountPrint);
				AmountInput.setText("");
				}
			}
		});
		
	}
}
class SearchDialog extends JDialog{
	
	JPanel SE1 = new JPanel(new FlowLayout());
	JPanel SE2 = new JPanel(new FlowLayout());
	JPanel SE3 = new JPanel(new FlowLayout());
	
	JTextField NameInput = new JTextField(12);
	JTextField NameOutput = new JTextField(15);
	JTextField DateOutput = new JTextField(15);
	JTextField AuthorOutput = new JTextField(15);
	JTextField PublisherOutput = new JTextField(15);
	JTextField PriceOutput = new JTextField(15);
	JTextArea BookContentOutput = new JTextArea(7,18);
	
	JLabel NameLabel = new JLabel("이름");
	JLabel DateLabel = new JLabel("날짜");
	JLabel AuthorLabel = new JLabel("작가");
	JLabel PublisherLabel = new JLabel("출판");
	JLabel PriceLabel = new JLabel("가격");
	JLabel ContentLabel = new JLabel("도서 설명");
	
	JTextArea Information = new JTextArea(2,18);
	JButton Confirm = new JButton("확인");
	JButton Exit = new JButton("종료");
	
	String SearchName;
	String InputSearch;
	String InformName;
	String InformDate;
	String InformAuthor;
	String InformPublisher;
	String InformPrice;
	String InformQuantity;
	
	int Num = -1;
	
	public SearchDialog(JFrame frame, String title){
		super(frame,title);
		
		add("North",SE1);
		SE1.add(Information);
		add("Center",SE2);
		SE2.add(NameInput);
		SE2.add(Confirm);
		SE2.add(NameLabel);
		SE2.add(NameOutput);
		SE2.add(DateLabel);
		SE2.add(DateOutput);
		SE2.add(AuthorLabel);
		SE2.add(AuthorOutput);
		SE2.add(PublisherLabel);
		SE2.add(PublisherOutput);
		SE2.add(PriceLabel);
		SE2.add(PriceOutput);
		SE2.add(ContentLabel);
		SE2.add(BookContentOutput);
		SE2.add(new JScrollPane(BookContentOutput));
		add("South",SE3);
		SE3.add(Exit);
		
		setSize(225,450);
		
		Information.setText("도서의 이름을 아래에 입력해주세요."
				+ "\n도서의 정보가 표시됩니다.");
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1);
		Information.setBorder(BorderFactory.createCompoundBorder(lineBorder,null));
		
		NameOutput.setEditable(false);
		DateOutput.setEditable(false);
		AuthorOutput.setEditable(false);
		PublisherOutput.setEditable(false);
		PriceOutput.setEditable(false);
		BookContentOutput.setEditable(false);
		Information.setEditable(false);
		
		BookContentOutput.setLineWrap(true);
		BookContentOutput.setBorder(BorderFactory.createCompoundBorder(lineBorder,null));
		
		//https://recipes4dev.tistory.com/55
		
		Confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SearchName = String.valueOf(NameInput.getText());
				for(int i=0;i<Name.size();i++) {
					if(SearchName.equals(Name.get(i))){
						Num = i;
						break;
						}
					}			
				
				
				if(NameInput.getText().length() == 0) {
					JOptionPane.showMessageDialog(null,"이름을 입력하지 않았습니다.","오류!",JOptionPane.ERROR_MESSAGE);
				}
				
				else if(Num == -1){
					JOptionPane.showMessageDialog(null,"찾을 수 없는 도서입니다.","오류!",JOptionPane.ERROR_MESSAGE);
				}
				
				else {
				NameOutput.setText(Name.get(Num));
				DateOutput.setText(Date.get(Num));
				AuthorOutput.setText(Author.get(Num));
				PublisherOutput.setText(Publisher.get(Num));
				PriceOutput.setText(Price.get(Num));
				BookContentOutput.setText(Content.get(Num));
				Num = -1;
					}
				}
			});	
		
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				NameInput.setText("");
				NameOutput.setText("");
				DateOutput.setText("");
				AuthorOutput.setText("");
				PublisherOutput.setText("");
				PriceOutput.setText("");
				BookContentOutput.setText("");
			}	
		});
		
		}
	}

public static void main(String [] args) {
	new Main();
	}
}
