package servlet;

public class TableInfo {
	private int session_id;
	private int table_id;
	private String session_status;

	public TableInfo(int s_id, int t_id, String s_status) {
		this.session_id = s_id;
		this.table_id = t_id;
		this.session_status = s_status;
	}

	public int getSession_id() {
		return session_id;
	}

	public int getTable_id() {
		return table_id;
	}

	public String getSession_status() {
		return session_status;
	}

	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}

	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}

	public void setSession_status(String session_status) {
		this.session_status = session_status;
	}
}
