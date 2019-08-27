package util.report;

public class ReportSize {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
/**
PrinterJob prnJob = PrinterJob.getPrinterJob();
PageFormat pf=prnJob.defaultPage();//得到默認設置
Paper paper=pf.getPaper();//得到頁面格式紙張
//paper.setSize(595,842);//A4紙的大小
paper.setSize(595,397);// pfyl 的異型紙
//paper.setSize(612,792);// letter 的紙型
paper.setImageableArea(0,0,paper.getWidth(),paper.getHeight());
pf.setPaper(paper);

pf.setOrientation(PageFormat.PORTRAIT);
//pf.setOrientation(PageFormat.LANDSCAPE);
prnJob.setPrintable(this,pf);

m_maxNumPage = 1;
prnJob.print();



//=====================
	DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
	PrintRequestAttributeSet aset = 
	new HashPrintRequestAttributeSet();
	aset.add(MediaSizeName.ISO_A4);//改此處size試試
	aset.add(new Copies(2));
	aset.add(Sides.TWO_SIDED_LONG_EDGE);
	aset.add(Finishings.STAPLE);

	//locate a print service that can handle it
	PrintService[] pservices =
	PrintServiceLookup.lookupPrintServices(flavor, aset);
	if (pservices.length > 0) {
		System.out.println("selected printer " + pservices[0].getName());

		//create a print job for the chosen service
		DocPrintJob pj = pservices[0].createPrintJob();
		try {

			FileInputStream fis = new FileInputStream("example.ps");
			Doc doc = new SimpleDoc(fis, flavor, null);
			//print the doc as specified
			pj.print(doc, aset);
		} catch (IOException ie) { 
			System.err.println(ie);
		} catch (PrintException e) { 
			System.err.println(e);
		}
	}
**/

}
