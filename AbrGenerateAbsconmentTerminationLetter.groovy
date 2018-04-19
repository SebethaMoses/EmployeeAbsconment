import com.egis.DocumentModel
import com.egis.data.Form
import com.egis.kernel.Kernel
import com.egis.kernel.db.DbManager
import com.egis.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory

DocumentModel doc = doc
DbManager db = Kernel.get(DbManager.class)
Logger log = LoggerFactory.getLogger("com.egis.transnet")
Session session = Kernel.get(Session.class)

def form = doc.session.spawnForm(db.resolve(Form.class, "Absconment Termination Letter"))
def values = doc.signature().savedValues + [request_formNo: doc.formNo]

values.keySet().removeAll(values.findAll {
    it.key.contains('signature') || it.key.toLowerCase().startsWith("label")
}.keySet())

values.remove('date')
values.remove('formNo')
form.signature().setDefaultValues(values)
doc.request_formNo = form.formNo
doc.employee_sap_no = form.employee_sap_no
String request_formNo = doc.request_formNo
doc.start_date = form.start_date
doc.employee_name = form.employee_name
doc.name = form.employee_name

form.allocate('AbsconmentTerminationLetter')



