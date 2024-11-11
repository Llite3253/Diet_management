package main;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

class Main {
	public static void main(String[] args) {
		new Login_GUI();
	}
}

//로그인 메인 화면
class Login_GUI extends JFrame implements ActionListener{
	JFrame L_main_F = new JFrame();
	JTextField L_id_TF = new JTextField(20);
	JTextField L_pw_TF = new JPasswordField(20);
    JLabel L_id_L = new JLabel();
	JLabel L_pw_L = new JLabel();
	JLabel L_error_L = new JLabel();
	JLabel L_error_L2 = new JLabel();
	
	Api api = new Api();
	
	Login_User_O LUO = new Login_User_O();
	
	public Login_GUI() {
		L_main_F.setSize(400,600);
		L_main_F.setLocationRelativeTo(null);
		L_main_F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		L_main_F.setResizable(false);
		L_main_F.getContentPane().setLayout(null);
		
		Container L_main_C = L_main_F.getContentPane();

        L_id_L.setText("아 이 디");
        L_id_L.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        L_id_L.setBounds(50, 150, 100, 30);
		L_id_TF.setBounds(150, 150, 200, 30);
		
		L_pw_L.setText("비 밀 번 호");
		L_pw_L.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		L_pw_L.setBounds(40, 200, 100, 30);
		L_pw_TF.setBounds(150, 200, 200, 30);
		
		L_error_L.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		L_error_L.setBounds(0, 230, 400, 30);
		L_error_L.setHorizontalAlignment(JLabel.CENTER);
		L_error_L.setForeground(Color.red);
		
		L_error_L2.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		L_error_L2.setBounds(0, 250, 400, 30);
		L_error_L2.setHorizontalAlignment(JLabel.CENTER);
		L_error_L2.setForeground(Color.red);
		
		JButton L_login_B = new JButton("로 그 인");
		L_login_B.setFont(new Font("맑은 고딕", 0, 15));
		L_login_B.setBackground(Color.white);
		L_login_B.setBounds(40, 300, 300, 30);
		
		JButton L_register_B = new JButton("회 원 가 입");
		L_register_B.setFont(new Font("맑은 고딕", 0, 15));
		L_register_B.setBackground(Color.white);
		L_register_B.setBounds(40, 340, 300, 30);

		L_register_B.addActionListener(this);
		L_login_B.addActionListener(this);
		
		L_main_C.add(L_id_L);
		L_main_C.add(L_id_TF);
		L_main_C.add(L_pw_L);
		L_main_C.add(L_pw_TF);
		L_main_C.add(L_error_L);
		L_main_C.add(L_error_L2);
		L_main_C.add(L_login_B);
		L_main_C.add(L_register_B);
		
		L_main_F.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "로 그 인") {
			Login_User_O user = api.loginUser(L_id_TF.getText(), L_pw_TF.getText());
			if(!(user == null)) {
				String user_session = L_id_TF.getText();
				Calendar_GUI CG = new Calendar_GUI(user_session);
				L_main_F.setVisible(false);
			}
			else {
				L_error_L.setText("아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.");
				L_error_L2.setText("입력하신 내용을 다시 확인해주세요.");
			}
		}
		else if(e.getActionCommand() == "회 원 가 입") {
			Register_GUI RG = new Register_GUI();
		}
	}
}

//회원가입 메인 화면
class Register_GUI extends JFrame implements ActionListener{
	JFrame R_main_F = new JFrame();
	JTextField R_id_TF = new JTextField(20);
	JTextField R_pw_TF = new JPasswordField(20);
	JTextField R_pw2_TF = new JPasswordField(20);
	JTextField R_name_TF = new JTextField(20);
	JLabel R_id_L = new JLabel();
	JLabel R_pw_L = new JLabel();
	JLabel R_pw2_L = new JLabel();
	JLabel R_name_L = new JLabel();
	JLabel R_id_error_L = new JLabel();
	JLabel R_pw_error_L = new JLabel();
	JLabel R_pw2_error_L = new JLabel();
	JLabel R_name_error_L = new JLabel();
	
	Api api = new Api();
	
	public Register_GUI() {
		R_main_F.setSize(400,600);
		R_main_F.setLocationRelativeTo(null);
		R_main_F.setResizable(false);
		R_main_F.getContentPane().setLayout(null);
		
		Container R_main_C = R_main_F.getContentPane();

		R_id_L.setText("아 이 디");
		R_id_L.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		R_id_L.setBounds(30, 50, 100, 30);
		R_id_TF.setBounds(30, 80, 330, 30); //250
		
		R_id_error_L.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		R_id_error_L.setBounds(30, 110, 300, 30);
		R_id_error_L.setForeground(Color.red);
		
		R_pw_L.setText("비 밀 번 호");
		R_pw_L.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		R_pw_L.setBounds(30, 130, 100, 30);
		R_pw_TF.setBounds(30, 160, 330, 30);
		
		R_pw_error_L.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		R_pw_error_L.setBounds(30, 190, 300, 30);
		R_pw_error_L.setForeground(Color.red);
		
		R_pw2_L.setText("비 밀 번 호  확 인");
		R_pw2_L.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		R_pw2_L.setBounds(30, 210, 200, 30);
		R_pw2_TF.setBounds(30, 240, 330, 30);
		
		R_pw2_error_L.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		R_pw2_error_L.setBounds(30, 270, 300, 30);
		R_pw2_error_L.setForeground(Color.red);
		
		R_name_L.setText("이 름");
		R_name_L.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		R_name_L.setBounds(30, 290, 100, 30);
		R_name_TF.setBounds(30, 320, 330, 30);
		
		R_name_error_L.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		R_name_error_L.setBounds(30, 350, 300, 30);
		R_name_error_L.setForeground(Color.red);
		
		JButton R_register_B = new JButton("회 원 가 입");
		R_register_B.setFont(new Font("맑은 고딕", 0, 15));
		R_register_B.setBackground(Color.white);
		R_register_B.setBounds(30, 440, 330, 30);
		R_register_B.addActionListener(this);
		
		R_main_C.add(R_id_L);
		R_main_C.add(R_id_TF);
		R_main_C.add(R_pw_L);
		R_main_C.add(R_pw_TF);
		R_main_C.add(R_pw2_L);
		R_main_C.add(R_pw2_TF);
		R_main_C.add(R_name_L);
		R_main_C.add(R_name_TF);
		R_main_C.add(R_id_error_L);
		R_main_C.add(R_pw_error_L);
		R_main_C.add(R_pw2_error_L);
		R_main_C.add(R_name_error_L);
		R_main_C.add(R_register_B);
		
		R_main_F.setVisible(true);
	}
	
	public void id_check() {
		Register_id_C RIC = api.RegisterIdC(R_id_TF.getText());
		R_id_error_L.setText("");
		R_id_error_L.setForeground(Color.red);
		if(R_id_TF.getText().length() > 0) {
			if(R_id_TF.getText().length() <= 20 && R_id_TF.getText().length() >= 5) {
				if(RIC != null) {
					R_id_error_L.setForeground(new Color(0,90,0));
					R_id_error_L.setText("멋진 아이디입니다!");
				}
				else {R_id_error_L.setText("이미 사용중이거나 탈퇴한 아이디입니다.");}
			}
			else {R_id_error_L.setText("5~20자 이하로 사용하세요");}
		}
		else {R_id_error_L.setText("필수 정보입니다.");}
	}
	
	public void pw_check() {
		R_pw_error_L.setText("");
		R_pw_error_L.setForeground(Color.red);
		if(R_pw_TF.getText().length() > 0) {
			if(R_pw_TF.getText().length() <= 16 && R_pw_TF.getText().length() >= 8) {
				R_pw_error_L.setForeground(new Color(0,90,0));
				R_pw_error_L.setText("안전합니다!");
			}
			else {R_pw_error_L.setText("8~16자 이하로 사용하세요");}
		}
		else {R_pw_error_L.setText("필수 정보입니다.");}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "회 원 가 입") {
			id_check();
			if(R_id_error_L.getText() == "멋진 아이디입니다!") {
				pw_check();
				if(R_pw_error_L.getText() == "안전합니다!") {
					if(R_pw_TF.getText().equals(R_pw2_TF.getText())) {
						R_pw2_error_L.setForeground(new Color(0,90,0));
						R_pw2_error_L.setText("비밀번호가 일치합니다.");
						if(R_name_TF.getText() == null) {
							R_name_error_L.setText("이름을 입력해주세요.");
						}
						else {
							R_name_error_L.setForeground(new Color(0,90,0));
							R_name_error_L.setText("확인되었습니다.");
							api.Register(R_id_TF.getText(), R_pw_TF.getText(), R_name_TF.getText());
							R_main_F.setVisible(false);
						}
					}
					else {
						R_pw2_error_L.setText("비밀번호가 일치하지 않습니다.");
					}
				}
			}
		}
	}
}

//달력 생성
class Calendar_GUI extends JFrame implements ActionListener{
	private String user_session;
	JFrame s_main_F = new JFrame();
	LayoutManager s_main_L = new GridLayout(7,7);
	JPanel s_calendar_P = new JPanel(s_main_L);

	JLabel date_L = new JLabel();
	
	JButton date_L_B = new JButton("◀");
	JButton date_R_B = new JButton("▶");

	JButton[] date_B = new JButton[49];
	
	JScrollPane date_S = new JScrollPane();
	
    JButton date_P_B = new JButton();
    
	
	CalendarFunction cF = new CalendarFunction();
	Api api = new Api();
	
    int temp_week = cF.week;

	public Calendar_GUI(String user_session) {
		start();
		this.user_session = user_session;
		
		s_main_F.setSize(400,600);
		s_main_F.setLocationRelativeTo(null);
		s_main_F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s_main_F.setResizable(false);
		s_main_F.getContentPane().setLayout(null);
		
		Container s_main_C = s_main_F.getContentPane();
		s_calendar_P.setBounds(5, 30, 375, 200);
		
		date_L.setFont(new Font("맑은 고딕", 0, 15));
		date_L.setBounds(60, 5, 264, 25);
		date_L.setHorizontalAlignment(JLabel.CENTER);
		
		date_L_B.setFont(new Font("맑은 고딕", 0, 15));
		date_L_B.setBounds(7, 5, 53, 25);
		date_L_B.setBackground(Color.white);
	
		date_R_B.setFont(new Font("맑은 고딕", 0, 15));
		date_R_B.setBounds(325, 5, 53, 25);
		date_R_B.setBackground(Color.white);
		
		date_S.setBounds(7,230,372,280);
		date_S.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		date_S.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        date_P_B.setText("추가하기");
        date_P_B.setFont(new Font("맑은 고딕", 0, 10));
        date_P_B.setBounds(160, 520, 80, 30);
        date_P_B.setBackground(Color.white);
		
		for(int i = 0; i < date_B.length; i++) {
			date_B[i] = new JButton();
			s_calendar_P.add(date_B[i]);
			date_B[i].setFont(new Font("맑은 고딕", 0, 15));
			date_B[i].setBackground(Color.white);
			date_B[i].addActionListener(this);
		}
		cF.setButtons(date_B);
		cF.setDay();
		date_B[cF.week + 7].setBackground(Color.gray);
		date_L.setText(cF.getCalText());
		Food_List(String.valueOf(cF.week));

		s_main_C.add(date_S);
		s_main_C.add(date_P_B);	
		s_main_C.add(date_L);
		s_main_C.add(date_L_B);
		s_main_C.add(date_R_B);
		s_main_C.add(s_calendar_P);
		
		s_main_F.setVisible(true);
		
	}
	private void start() {
		date_L_B.addActionListener(this);
		date_R_B.addActionListener(this);
		date_P_B.addActionListener(this);
	}
	
	public void Food_List(String e) {
		List<FoodMember> ls = api.selectListFoodMember(user_session, cF.year, cF.month, Integer.parseInt(e));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1, ls.size()*106));
		
		
		for (FoodMember m : ls) {
			FoodNutrients food = api.select_food_id(m.getFoodid());
			
			JPanel panel1 = new JPanel();
	        panel1.setBackground(Color.white);
	        panel1.setPreferredSize(new Dimension(345, 100));
	        panel1.setLayout(null);
	        
	        JLabel label1 = new JLabel("[ " + m.getType() + " ]");
	        label1.setBounds(10, 0, 300, 30);
	        JLabel label2 = new JLabel(food.getFood_name());
	        label2.setBounds(10, 20, 300, 30);
	        JLabel label3 = new JLabel(food.getFood_calorie() + "kcal   탄 " + food.getFood_carbohydrate() + "g   단 " + food.getFood_protein() + "g");
	        label3.setBounds(10, 40, 200, 30);
	        JLabel label4 = new JLabel("지 " + food.getFood_fat() + "g   당" + food.getFood_sugar() + "g   나 " + food.getFood_sodium() + "mg");
	        label4.setBounds(10, 60, 200, 30);
	        
	        CustomValueButton button1 = new CustomValueButton("x", m.getTable_id());
	        button1.setBounds(290, 28, 45, 45);
	        button1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
	        button1.setBackground(Color.white);
	        button1.addActionListener(this);
	        
	        panel1.add(label1);
	        panel1.add(label2);
	        panel1.add(label3);
	        panel1.add(label4);
	        panel1.add(button1);
	        panel1.setBorder(new TitledBorder(new LineBorder(Color.black,2)));
	        mainPanel.add(panel1);
		}
		
		
        date_S.setViewportView(mainPanel);
	}
	
	private String[] select_food_id(int foodid) {
		// TODO Auto-generated method stub
		return null;
	}

	public class CustomValueButton extends JButton {
	    private int customValue;

	    public CustomValueButton(String text, int customValue) {
	        super(text);
	        this.customValue = customValue;
	    }

	    public int getCustomValue() {
	        return customValue;
	    }
	    public void setCustomValue(int customValue) {
	        this.customValue = customValue;
	    }
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == date_L_B || e.getSource() == date_R_B ) {
			int gap = 0;
			if(e.getSource() == date_L_B) {
				gap = -1;
			}
			else if(e.getSource() == date_R_B) {
				gap = 1;
			}
			cF.allremove(gap);
			date_L.setText(cF.getCalText());
		}
		else if(e.getActionCommand().chars().allMatch(Character::isDigit) && !e.getActionCommand().isEmpty()) {
			Food_List(e.getActionCommand());
			int start = cF.cal.get(Calendar.DAY_OF_WEEK) - 1;
			for(int i = 1; i <= cF.cal.getActualMaximum(Calendar.DATE); i++){
				date_B[6 + start + i].setBackground(Color.white);
				if(date_B[6 + start + i].getText() == e.getActionCommand()) {
					date_B[6 + start + i].setBackground(Color.gray);
					temp_week = Integer.parseInt(e.getActionCommand());
				}
			}
		}
		else if(e.getActionCommand() == "x") {
			CustomValueButton button  = (CustomValueButton)e.getSource();
			api.deleteFoodMember(button.getCustomValue());
		}
		if(e.getActionCommand() == "추가하기") {
			Food_Add_GUI FAG = new Food_Add_GUI(user_session, temp_week);
		}
	}
}

//달력 유틸
class CalendarFunction{
	String date_week[] = {"일","월","화","수","목","금","토"};
	JButton[] date_B;
	Calendar cal = Calendar.getInstance();
	int year, month, week;
	int temp_week;
	
	public CalendarFunction() {
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		week = cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public void setButtons(JButton[] date_B) {
		this.date_B = date_B;
	}
	
	public String getCalText() {
		return year + "년 " + month + "월";
	}
	
	public void setDay() {
		cal.set(year, month-1, 1);
		int start = cal.get(Calendar.DAY_OF_WEEK) - 1;
		for(int i = 0; i < 7; i++) {
			date_B[i].setText(date_week[i]);
		}
		for(int i = 1; i <= cal.getActualMaximum(Calendar.DATE); i++){
			date_B[6 + start + i].setText(String.valueOf(i));
		}
	}
	
	public void allremove(int gap) {
		for(int i = 7; i < date_B.length; i++) {
			date_B[i].setText("");
			date_B[i].setBackground(Color.white);
		}
		month += gap;
		if(month <= 0) {
			year--;
			month = 12;
		}
		else if(month >= 13) {
			year++;
			month = 1;
		}
		setDay();
	}
}

//추가하기
class Food_Add_GUI extends JFrame implements ActionListener{
	private String user_session;
	private int temp_week;
	
	JFrame add_F = new JFrame();
	JTextField add_TF = new JTextField(20);
	JPanel add_P = new JPanel();
	JButton add_S_B = new JButton();
	JButton[] add_C_B = new JButton[6];
	JScrollPane add_S = new JScrollPane();
	JLabel add_error_L = new JLabel();
	
	String Classification[] = {"아침","아점","점심","점저","저녁","야식"};
	int add_F_temp = 0;
	Api api = new Api();
	CalendarFunction cF = new CalendarFunction();
	public Food_Add_GUI(String user_session, int temp_week) {
		this.user_session = user_session;
		this.temp_week = temp_week;
		
		start();
		add_F.setSize(400, 600);
		add_F.setLocationRelativeTo(null);
		add_F.setResizable(false);
		add_F.getContentPane().setLayout(null);
		
		Container add_T_C = add_F.getContentPane();

		add_TF.setBounds(120,10,150,30);
		
		add_S_B.setText("검색");
		add_S_B.setBounds(270, 10, 60, 30);
		add_S_B.setBackground(Color.white);
		add_S_B.setFont(new Font("맑은 고딕", 0, 12));
		
		JLabel add_L = new JLabel();
		add_L.setText("분류");
		add_L.setBounds(10, 40, 60, 30);
		add_L.setFont(new Font("맑은 고딕", 0, 12));

		add_P.setBounds(0, 70, 390, 30);
		
		for(int i = 0; i < add_C_B.length; i++) {
			add_C_B[i] = new JButton();
			add_C_B[i].setFont(new Font("맑은 고딕", 0, 10));
			add_C_B[i].setBackground(Color.white);
			add_C_B[i].setText(Classification[i]);
			add_C_B[i].addActionListener(this);
			add_P.add(add_C_B[i]);
		}
		
		add_error_L.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		add_error_L.setBounds(20, 100, 300, 30);
		add_error_L.setForeground(Color.red);
		
		add_S.setBounds(7,230,372,280);
		add_S.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add_S.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add_T_C.add(add_TF);
		add_T_C.add(add_S_B);
		add_T_C.add(add_L);
		add_T_C.add(add_error_L);
		add_T_C.add(add_P);
		add_T_C.add(add_S);
		
		add_F.setVisible(true);
	}
	
	private void start() {
		add_S_B.addActionListener(this);
	}
	
	public void add_select(){
		List<FoodNutrients> ls = api.selectListFood(add_TF.getText());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1, ls.size()*65));
        
		for (FoodNutrients m : ls) {
			JPanel panel1 = new JPanel();
	        panel1.setBackground(Color.white);
	        panel1.setPreferredSize(new Dimension(345, 60));
	        panel1.setLayout(null);
	        panel1.setBorder(new TitledBorder(new LineBorder(Color.black,2)));
	        
	        JLabel label1 = new JLabel(m.getFood_name());
	        label1.setBounds(10, 0, 300, 30);
	        label1.setFont(new Font("맑은 고딕", 0, 20));
	        JLabel label2 = new JLabel(m.getFood_calorie() + " kcal");
	        label2.setBounds(10, 30, 300, 30);
	        label2.setFont(new Font("맑은 고딕", 0, 15));
	        
	        CustomValueButton button1 = new CustomValueButton("+", m.getFood_name());
	        button1.setBounds(290, 8, 45, 45);
	        button1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
	        button1.setBackground(Color.white);
	        
	        panel1.add(label1);
	        panel1.add(label2);
	        panel1.add(button1);
	        mainPanel.add(panel1);

	        button1.addActionListener(this);
		}
		add_S.setViewportView(mainPanel);
	}

	public class CustomValueButton extends JButton {
	    private String customValue;

	    public CustomValueButton(String text, String customValue) {
	        super(text);
	        this.customValue = customValue;
	    }

	    public String getCustomValue() {
	        return customValue;
	    }

	    public void setCustomValue(String customValue) {
	        this.customValue = customValue;
	    }
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "검색") {
			add_select();
		}
		else if(e.getActionCommand() == "+") {
			CustomValueButton button  = (CustomValueButton)e.getSource();
			for(int i = 0; i < add_C_B.length; i++) {
				if(add_C_B[i].getBackground() == Color.gray) {
					FoodNutrients id = api.selectFood(button.customValue);
					api.insert_food_member(user_session, cF.year, cF.month, temp_week, add_C_B[i].getText(), id.getFood_id());
					add_F.setVisible(false);
				}
				else {
					add_error_L.setText("분류를 선택해주세요!");
				}
			}
		}
		else if(e.getActionCommand() == Classification[Arrays.asList(Classification).indexOf(e.getActionCommand())]) {
			if(add_C_B[Arrays.asList(Classification).indexOf(e.getActionCommand())].getBackground() == Color.gray) {
				add_C_B[Arrays.asList(Classification).indexOf(e.getActionCommand())].setBackground(Color.white);
			}
			else {
				for(int i = 0; i < add_C_B.length; i++) {
					if(add_C_B[i].getBackground() == Color.gray) {
						add_C_B[i].setBackground(Color.white);
					}
				}
				add_C_B[Arrays.asList(Classification).indexOf(e.getActionCommand())].setBackground(Color.gray);
			}
		}
	}
	
}

// 로그인 객체 생성
class Login_User_O {
	private String user_id;
	private String user_pw;
	
	public Login_User_O() {}
	
	public Login_User_O(String user_id, String user_pw) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_year(String user_pw) {
		this.user_pw = user_pw;
	}
}

// 회원가입 객체 생성
class Register_id_C {
	private String user_id;
	
	public Register_id_C() {}
	
	public Register_id_C(String user_id) {
		super();
		this.user_id = user_id;
	}
	public String getUser_id() {
		return user_id;
	}
}

// 사용자 저장 음식 테이터 베이스 객체 생성
class FoodMember {
	private String user_id;
	private int user_year;
	private int user_month;
	private int user_week;
	private String type;
	private Long foodid;
	private int table_id;
	
	public FoodMember() {}
	
	public FoodMember(String user_id, int user_year, int user_month, int user_week,
			String type, Long foodid, int table_id) {
		super();
		this.user_id = user_id;
		this.user_year = user_year;
		this.user_month = user_month;
		this.user_week = user_week;
		this.type = type;
		this.foodid = foodid;
		this.table_id = table_id;
	}

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getUser_year() {
		return user_year;
	}
	public void setUser_year(int user_year) {
		this.user_year = user_year;
	}
	public int getUser_month() {
		return user_month;
	}
	public void setUser_month(int user_month) {
		this.user_month = user_month;
	}
	public int getUser_week() {
		return user_week;
	}
	public void setUser_week(int user_week) {
		this.user_week = user_week;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getFoodid() {
		return foodid;
	}
	public void setFoodid(Long foodid) {
		this.foodid = foodid;
	}
	public int getTable_id() {
		return table_id;
	}
	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}
}

// 음식 데이터 베이스 객체 생성
class FoodNutrients {
	private String food_name;
	private Long food_calorie;
	private Long food_carbohydrate;
	private Long food_protein;
	private Long food_fat;
	private Long food_sugar;
	private Long food_sodium;
	private Long food_id;
	
	public FoodNutrients() {}
	
	public FoodNutrients(String food_name, Long food_calorie, Long food_carbohydrate, 
			Long food_protein, Long food_fat, Long food_sugar, Long food_sodium, Long food_id) {
		super();
		this.food_name = food_name;
		this.food_calorie = food_calorie;
		this.food_carbohydrate = food_carbohydrate;
		this.food_protein = food_protein;
		this.food_fat = food_fat;
		this.food_sugar = food_sugar;
		this.food_sodium = food_sodium;
		this.food_id = food_id;
	}
	public String getFood_name(){
		return food_name;
	}
	public void setFood_name(String food_name){
		this.food_name = food_name;
	}
	public Long getFood_calorie(){
		return food_calorie;
	}
	public void setFood_calorie(Long food_calorie){
		this.food_calorie = food_calorie;
	}
	public Long getFood_carbohydrate(){
		return food_carbohydrate;
	}
	public void setFood_carbohydrate(Long food_carbohydrate){
		this.food_carbohydrate = food_carbohydrate;
	}
	public Long getFood_protein(){
		return food_protein;
	}
	public void setFood_protein(Long food_protein){
		this.food_protein = food_protein;
	}
	public Long getFood_fat(){
		return food_fat;
	}
	public void setFood_fat(Long food_fat){
		this.food_fat = food_fat;
	}
	public Long getFood_sugar(){
		return food_sugar;
	}
	public void setFood_suger(Long food_sugar){
		this.food_sugar = food_sugar;
	}
	public Long getFood_sodium(){
		return food_sodium;
	}
	public void setFood_sodium(Long food_sodium){
		this.food_sodium = food_sodium;
	}
	public Long getFood_id(){
		return food_id;
	}
	public void setFood_id(Long food_id){
		this.food_id = food_id;
	}
	
	public String toString() {
		return food_name + "/" + food_calorie + "/" + food_carbohydrate + "/" + food_protein + "/" + food_fat + "/" + food_sugar + "/" + food_sodium + "/" + food_id;
	}
}

class Api {
    private static final String BASE_URL = "http://localhost:3000";
    private HttpClient client;
    private Gson gson;

    public Api() {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

    // 공통 POST 요청 메서드
    private JsonObject sendPostRequest(String endpoint, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), JsonObject.class);
        } else {
            System.out.println("서버 오류: 상태 코드 " + response.statusCode());
            return null;
        }
    }
    
    public Login_User_O loginUser(String id, String pw) {
        try {
            String json = String.format("{\"id\":\"%s\", \"pw\":\"%s\"}", id, pw);
            JsonObject jsonResponse = sendPostRequest("/login", json);
            
            if (jsonResponse != null && jsonResponse.get("success").getAsBoolean()) {
                return new Login_User_O(id, pw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 회원가입 ID 중복 확인
    public Register_id_C RegisterIdC(String id) {
        try {
            String json = String.format("{\"id\":\"%s\"}", id);
            JsonObject jsonResponse = sendPostRequest("/register_C_id", json);

            if (jsonResponse != null && jsonResponse.get("success").getAsBoolean()) {
                return new Register_id_C(id); // 중복된 아이디가 있을 경우
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 중복된 아이디가 없을 경우
    }

    // 회원가입
    public void Register(String id, String pw, String name) {
        try {
            String json = String.format("{\"id\":\"%s\", \"pw\":\"%s\", \"name\":\"%s\"}", id, pw, name);
            JsonObject jsonResponse = sendPostRequest("/register", json);

//            if (jsonResponse != null) {
//                if (jsonResponse.get("success").getAsBoolean()) {
//                    System.out.println("회원가입 성공: " + jsonResponse.get("message").getAsString());
//                } else {
//                    System.out.println("회원가입 실패: " + jsonResponse.get("message").getAsString());
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // food_name으로 조회 API 호출
    public FoodNutrients selectFood(String foodName) {
        try {
            // GET 요청 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/select_food_name?food_name=" + foodName))
                    .GET()
                    .build();

            // 요청 보내고 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 코드가 200일 때만 JSON 파싱
            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

                if (jsonResponse.get("success").getAsBoolean()) {
                    JsonObject foodData = jsonResponse.getAsJsonObject("food");
                    return gson.fromJson(foodData, FoodNutrients.class); // JSON 데이터를 FoodNutrients 객체로 변환
                }
            } else {
                System.out.println("서버 오류: 상태 코드 " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 결과가 없거나 오류 발생 시 null 반환
    }
    
 // food_name으로 조회 API 호출
    public FoodNutrients select_food_id(Long id) {
        try {
            // GET 요청 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/select_food_id?food_id=" + id))
                    .GET()
                    .build();

            // 요청 보내고 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 코드가 200일 때만 JSON 파싱
            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

                if (jsonResponse.get("success").getAsBoolean()) {
                    JsonObject foodData = jsonResponse.getAsJsonObject("food");
                    return gson.fromJson(foodData, FoodNutrients.class); // JSON 데이터를 FoodNutrients 객체로 변환
                }
            } else {
                System.out.println("서버 오류: 상태 코드 " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 결과가 없거나 오류 발생 시 null 반환
    }
    
    // food_name으로 검색하여 FoodNutrients 리스트 반환
    public List<FoodNutrients> selectListFood(String foodName) {
        try {
            // GET 요청 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/select_food_list?food_name=" + foodName))
                    .GET()
                    .build();

            // 요청 전송 및 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

                if (jsonResponse.get("success").getAsBoolean()) {
                    // JSON 배열을 List<FoodNutrients>로 변환
                    Type listType = new TypeToken<List<FoodNutrients>>(){}.getType();
                    return gson.fromJson(jsonResponse.get("foods"), listType);
                }
            } else {
                System.out.println("서버 오류: 상태 코드 " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 결과가 없거나 오류 발생 시 null 반환
    }
    
    public void insert_food_member(String user_id, int year, int month, int week, String type, Long foodid) {
        try {
            // 올바른 JSON 문자열 생성
            String json = String.format("{\"user_id\":\"%s\", \"year\":%d, \"month\":%d, \"week\":%d, \"type\":\"%s\", \"foodid\":%d}",
                    user_id, year, month, week, type, foodid);

            // POST 요청 전송 및 응답 처리
            JsonObject jsonResponse = sendPostRequest("/insert_food_member", json);

//            if (jsonResponse != null && jsonResponse.get("success").getAsBoolean()) {
//                System.out.println("삽입 성공: " + jsonResponse.get("message").getAsString());
//            } else {
//                System.out.println("삽입 실패: " + (jsonResponse != null ? jsonResponse.get("message").getAsString() : "Unknown error"));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 사용자의 Food_M 데이터 가져오기
    public List<FoodMember> selectListFoodMember(String user_id, int year, int month, int week) {
        try {
            // GET 요청 URI 생성
            String uri = String.format("%s/select_food_member_list?user_id=%s&year=%d&month=%d&week=%d",
                    BASE_URL, user_id, year, month, week);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();

            // 요청 전송 및 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

                if (jsonResponse.get("success").getAsBoolean()) {
                    // JSON 배열을 List<FoodMember>로 변환
                    Type listType = new TypeToken<List<FoodMember>>(){}.getType();
                    return gson.fromJson(jsonResponse.get("foodMembers"), listType);
                }
            } 
//            else {
//                System.out.println("서버 오류: 상태 코드 " + response.statusCode());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(); // 오류 시 빈 리스트 반환
    }
    
    // 삭제 요청 메서드
    public void deleteFoodMember(int table_id) {
        try {
            // JSON 형식의 요청 바디 생성
            String json = String.format("{\"table_id\":%d}", table_id);
            
            // DELETE 요청 설정
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/deleteFoodmember"))
                    .header("Content-Type", "application/json")
                    .method("DELETE", HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // 요청 전송 및 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                if (jsonResponse.get("success").getAsBoolean()) {
                    System.out.println("삭제 성공: " + jsonResponse.get("message").getAsString());
                } else {
                    System.out.println("삭제 실패: " + jsonResponse.get("error").getAsString());
                }
            } else {
                System.out.println("서버 오류: 상태 코드 " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}