import java.util.HashMap;

import org.hardcode.juf.InstallException;
import org.hardcode.juf.Installer;
import org.hardcode.juf.JUpdateUtilities;
import org.hardcode.juf.ProgressListener;
import org.hardcode.juf.status.Status;
import org.hardcode.juf.status.UpdateInfo;

import com.thinkingtop.kaas.etl.result.Result;
import com.thinkingtop.kaas.etl.writer.ResultWriter;


public class Samle implements Installer{

	@Override
	public UpdateInfo install(HashMap clientStatus, UpdateInfo status,
			ProgressListener listener) throws InstallException {
		JUpdateUtilities jup = new JUpdateUtilities();
		Status componentStatus = jup.getComponentStatus(status, "sample-component");

		if (componentStatus == null){
		componentStatus = new Status();
		componentStatus.setComponentName("sample-component");
		componentStatus.setVersion(new Long("1"));
		status.addStatus(componentStatus);
		}else{
		componentStatus.setVersion(componentStatus.getVersion() + 1);
		} 
		
		ResultWriter.getInstance().writeToXml(new Result().getResult());
		return status;
	}

}
