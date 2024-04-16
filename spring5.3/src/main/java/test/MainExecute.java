package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainExecute {

	public static void main(String[] args) throws Exception {
		System.out.println("시작");
		MainExecute me = new MainExecute();
		me.showAllFile("D:\\workDir\\openjdk-1.8\\include");
		System.out.println("종료");
	}

	public void showAllFile(String path) throws Exception {
		File root = new File(path);
		System.out.println("경로=" + root.getAbsolutePath() + "|디렉토리 여부=" + root.isDirectory());

		File files[] = root.listFiles();
		for (int i = 0; i < files.length; i++) {
			File tempFile = files[i];
			if (tempFile.isDirectory()) {
				showAllFile(tempFile.getPath());
			} else {
				System.out.println("fileName=" + files[i].getName() + "|fileInfo=" + files[i]);
				printLine(files[i].getPath());
			}
		}
	}

	public void printLine(String filePath) throws Exception {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String str;
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
	}

}
