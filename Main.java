public class Main {
	
	public static void main(String[] args) {
		
		new Thread(){
			public void run(){
				Panel infoDisplay = new Panel();
				infoDisplay.setServerPort("localhost", 2000);
			}
		}.start();
	}
}