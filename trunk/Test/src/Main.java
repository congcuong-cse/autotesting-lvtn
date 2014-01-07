package hcmut.cse;

public class Main {
	
	public int _fn01(){
		int a = 4;
		return a;
	}
	
	public int _fn02(int a){
		if(a==0){
			return 1/a;
		}
		else
			return a;
	}
	
	public int _fn03(int a){
		if(a<0){
			return a;
		}
		else if(a==0){
			return 1/a;
		}
		else
			return a;
	}
	
	public int _fn04( int c, int d){
		if(!(d==0 && c==6)){
			d= d+1;
			return d;
		}
		else
			return c;
	}
	
	public void _fn05(int a, int x, int y){
		switch (a){  
			case 1:
				System.out.println("Enter the number one=" + (x+y));
				break;
			case 2:
				System.out.println("Enter the number two=" + (x-y));
				break;
			case 3:
				System.out.println("Enetr the number three="+ (x*y));
				break;
			case 4:
				System.out.println("Enter the number four="+ (x/y));
				break;
			default:
				System.out.println("Invalid Entry!");
		}
	}
	
	public int _fn06( int a, int b){
		if(a<0){
			if(b<0){
				return 1;
			}
			else{
				return 2;
			}
		}
		else if(a==0){
			if(b<0){
				return 3;
			}
			else{
				return 4;
			}
		}
		else{
			if(b<0){
				return 5;
			}
			else{
				return 6;
			}
		}
	}
	public int _fn07( int a, int b){
		if(a<0){
			if(b<0){
				return 1;
			}
			else if (b==0){
				return 2;
			}
			else{
				return 3;
			}
		}
		else if(a==0){
			if(b<0){
				return 4;
			}
			else if (b==0){
				return 5;
			}
			else{
				return 6;
			}
		}
		else{
			if(b<0){
				return 7;
			}
			else if (b==0){
				return 8;
			}
			else{
				return 9;
			}
		}
	}
	
	public int _fn08(int b, int c){
		switch (b){
			case -1:
				if( c == 4)
				c = 9;
				else
					break;
			case 0: 
			case 2:
				c =1;
				break;
			default:
				b = 3;
				break;
		}
		return b;
	}
	
	public int _fn09( int c, int d){
		if(!(d==0 && c==6)){
			d= d+1;
			return d;
		}
		else
			d = c;
		if(d>=5){
			d = d+2;
		}
		else
			return c;
		return 4;
	}
	
	public int _fn10( int a, int b, int c){
		if( !( !(a==0 && b==3) || c==1 )){
			return 1;
		}
		else
			return 0;
	}
	
	public int _fn11( int a, int b, int c, int d){
		if( !( !(a==0 && b==3) || c==1 || !(d>0))){
			return 1;
		}
		else
			return 0;
	}
	
	public int _fn12(int a){
		int i;
		for (i=0; a< 8 && a>0; i++){
			if(a<5)
				a += 2;
			else
				a += 1;
		}
		return a+i;
	}
	
	public int _fn13(int a){
		int i;
		i=0;
		while (a< 8 && a>0){
			if(a<5)
				a += 2;
			else
				a += 1;
			i++;
		}
		return a+i;
	}
	
	public int _fn14(int a){
		int i;
		i=0;
		do{
			if(a<5)
				a += 2;
			else
				a += 1;
			i++;
		}
		while (a< 8 && a>0);
		return a+i;
	}
	
	public int _fn15(int a, int b){
		int i;
		int j;
		for (i=0; i<3; i++){
			for (j=0; j <3; j++){
				a +=1;
				b +=1;
			}
		}
		return a+b;
	}
	
	public int _fn16(int a, int b){
		int i;
		int j;
		i=0;
		while (a>3 && a< 5){
			j=0;
			while (b>0 && b<3){
				a +=1;
				b +=1;
				j++;
			}
			i = i+j;
		}
		return i;
	}
	
	
	public int simple_for(int a){
		int i;
		for (i=0; a< 8 && a>0; i++){
			if(a<5)
				a += 2;
			else
				a += 1;
		}
		return a+i;
	}
	
	public int simple_while(int a){
		int i;
		i=0;
		while (a< 8 && a>0){
			if(a<5)
				a += 2;
			else
				a += 1;
			i++;
		}
		return a+i;
	}
	
	public int simple_do(int a){
		int i;
		i=0;
		do{
			if(a<5)
				a += 2;
			else
				a += 1;
			i++;
		}
		while (a< 8 && a>0);
		return a+i;
	}
	
	public int mutiple_for(int a, int b){
		int i;
		int j;
		for (i=0; i<3; i++){
			for (j=0; j <3; j++){
				a +=1;
				b +=1;
			}
		}
		return a+b;
	}
	
	public int mutiple_while(int a, int b){
		int i;
		int j;
		i=0;
		while (a>3 && a< 5){
			j=0;
			while (b>0 && b<3){
				a +=1;
				b +=1;
				j++;
			}
			i = i+j;
		}
		return i;
	}
	
	public int mutiple_do(int a, int b){
		int i;
		int j;
		i=0;
		do{
			j=0;
			do{
				a +=1;
				b +=1;
				j++;
			}
			while (j<10);
			i++;
		}
		while ( i<10);
		return a+b;
	}
	
	public int mutiple_if( int c, int d){
		if(!(d==0 && c==6)){
			d= d+1;
			return d;
		}
		else
			d = c;
		if(d>=5){
			d = d+2;
		}
		else
			return c;
		return 4;
	}
	
	public int mutiple_condition( int a, int b, int c, int d){
		if( !( !(a==0 && b==3) || a==1 || !(c==d))){
			return 1;
		}
		else
			return 0;
	}
	
	public int switch_test(int b, int c){
		switch (b){
			case -1:
				if( c == 4)
				c = 9;
				else
					break;
			case 0: 
			case 2:
				c =1;
				break;
			default:
				b = 3;
				break;
		}
		return b;
	}
	
	
	
	public int trycatch_test(int a){
		try{
			return 1/a;
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	
	public int nopara(){
		int a = 4;
		return a;
	}
	
	public void void_(){
		System.out.println("void_");
	}
	
	public int double_input(double a){
		if(a==3.14){
			return 1;
		}
		else
			return 0;
	}
	
	public boolean boolean_output(int a){
		if( a==0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public double average(int min, int max){
		int [] value = new int [] { 9, 9, 3 };
		int sum =0;
		int i =0;
		int tcnt =0;
		int vcnt =0;
		while(value[i] != 9 && tcnt < 10){
			tcnt ++;
			if(min<=value[i] && value[i]<=max){
				sum += value[i];
				vcnt ++;
			}
			i++;
		}
		return sum/vcnt;
	}
	
	public int test(int a){
		if(a==0){
			return 1/a;
		}
		else
			return a;
	}
	
	public void ternaryStatement(int a, int c){
		a = (a== 0  && c != 0)? 0 : 1;
	}
	
	public int a( int b, int c){
		int i;
		int j;
		for(i = 0, j=9; i<10 && c != 5; i++){
			c = c+1;
		}
		if (b==0){
			c = c -b;
			return c;
		}
		c= c+b;
		return c;
	}
	
	public int a1( int b, int c){
		do{
			c += 1;
		}
		while(c < 6 || c*b<100);
		if (b==0){
			c = c -b;
			return c;
		}
		c= c+b;
		return c;
	}
	
	
	public int b2( int c, int d){
		if(!(d==0 && c==0)){
			d= d+1;
		}
		else
			c = 3;
		if(c==1){
			d = d+2;
			return 4;
		}
		else
			return c;
		
	}
	
	
	public void c(){
		int c =1;
		c =2;
		c=3;
	}
	
	public int condition(boolean v1, boolean v11, boolean v2, boolean v3, boolean v4, boolean v5, boolean v6){
		if( (((v1 && v11) || v2)|| (v3 && v4)) && (!v5 && v6) ){
			return 1;
		}
		else
			return 0;
	}
	public boolean condition_all(boolean v1, boolean v2, boolean v3){
		if( v1 && !(v2 || v3))
			return true;
		else
			return false;
	}
	public boolean condition_and(boolean v1, boolean v2){
		if( v1 && v2){
			return true;
		}
		else
			return false;
	}
	public boolean condition_or(boolean v1, boolean v2){
		if( v1 || v2){
			return true;
		}
		else
			return false;
	}
	public boolean condition_not(boolean v1){
		if( !v1 ){
			return true;
		}
		else
			return false;
	}

}
