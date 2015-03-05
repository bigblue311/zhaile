using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.Runtime.InteropServices;
using SettingsAndLog4net;
using System.Text.RegularExpressions;
using System.Net;
using Newtonsoft.Json;
using System.IO;
using Newtonsoft.Json.Linq;

namespace zhaile.mc
{
	/// <summary>
	/// Form1 ��ժҪ˵����
	/// </summary>
	public class Form1 : System.Windows.Forms.Form
    {
        private GroupBox groupBox1;
        private Label deviceStatus;
        private Label label2;
        private Label label1;
        private Label networkStatus;
        private GroupBox groupBox2;
        private ToolStrip toolStrip1;
        private Timer taskTimer;
        private ListBox taskReport;
        private ToolStripButton toolStripButton1;
        private ToolStripButton toolStripButton2;
        private ToolStripButton toolStripButton3;
        private ToolStripButton toolStripButton4;
        private Timer healthCheck;
        private System.ComponentModel.IContainer components;

		public Form1()
		{
			//
			// Windows ���������֧���������
			//
			InitializeComponent();

			//
			// TODO: �� InitializeComponent ���ú�����κι��캯������
			//
		}

		/// <summary>
		/// ������������ʹ�õ���Դ��
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// �����֧������ķ��� - ��Ҫʹ�ô���༭���޸�
		/// �˷��������ݡ�
		/// </summary>
		private void InitializeComponent()
		{
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.networkStatus = new System.Windows.Forms.Label();
            this.deviceStatus = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.taskReport = new System.Windows.Forms.ListBox();
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.taskTimer = new System.Windows.Forms.Timer(this.components);
            this.toolStripButton1 = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton2 = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton3 = new System.Windows.Forms.ToolStripButton();
            this.toolStripButton4 = new System.Windows.Forms.ToolStripButton();
            this.healthCheck = new System.Windows.Forms.Timer(this.components);
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.toolStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.networkStatus);
            this.groupBox1.Controls.Add(this.deviceStatus);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(4, 28);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(290, 66);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "ϵͳ״̬";
            // 
            // networkStatus
            // 
            this.networkStatus.AutoSize = true;
            this.networkStatus.ForeColor = System.Drawing.Color.Blue;
            this.networkStatus.Location = new System.Drawing.Point(67, 42);
            this.networkStatus.Name = "networkStatus";
            this.networkStatus.Size = new System.Drawing.Size(83, 12);
            this.networkStatus.TabIndex = 3;
            this.networkStatus.Text = "����������...";
            // 
            // deviceStatus
            // 
            this.deviceStatus.AutoSize = true;
            this.deviceStatus.ForeColor = System.Drawing.Color.Red;
            this.deviceStatus.Location = new System.Drawing.Point(67, 21);
            this.deviceStatus.Name = "deviceStatus";
            this.deviceStatus.Size = new System.Drawing.Size(53, 12);
            this.deviceStatus.TabIndex = 2;
            this.deviceStatus.Text = "�޷�����";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(7, 42);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(65, 12);
            this.label2.TabIndex = 1;
            this.label2.Text = "�������ӣ�";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(7, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "�豸���ӣ�";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.taskReport);
            this.groupBox2.Location = new System.Drawing.Point(4, 102);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(290, 579);
            this.groupBox2.TabIndex = 1;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "��Ϣ����";
            // 
            // taskReport
            // 
            this.taskReport.FormattingEnabled = true;
            this.taskReport.ItemHeight = 12;
            this.taskReport.Location = new System.Drawing.Point(6, 20);
            this.taskReport.Name = "taskReport";
            this.taskReport.Size = new System.Drawing.Size(278, 556);
            this.taskReport.TabIndex = 1;
            // 
            // toolStrip1
            // 
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripButton1,
            this.toolStripButton2,
            this.toolStripButton3,
            this.toolStripButton4});
            this.toolStrip1.Location = new System.Drawing.Point(0, 0);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(298, 25);
            this.toolStrip1.TabIndex = 2;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // taskTimer
            // 
            this.taskTimer.Interval = 300000;
            this.taskTimer.Tick += new System.EventHandler(this.taskTimer_Tick);
            // 
            // toolStripButton1
            // 
            this.toolStripButton1.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripButton1.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton1.Image")));
            this.toolStripButton1.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton1.Name = "toolStripButton1";
            this.toolStripButton1.Size = new System.Drawing.Size(60, 22);
            this.toolStripButton1.Text = "ϵͳ����";
            this.toolStripButton1.Click += new System.EventHandler(this.toolStripButton1_Click);
            // 
            // toolStripButton2
            // 
            this.toolStripButton2.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripButton2.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton2.Image")));
            this.toolStripButton2.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton2.Name = "toolStripButton2";
            this.toolStripButton2.Size = new System.Drawing.Size(60, 22);
            this.toolStripButton2.Text = "��������";
            this.toolStripButton2.Click += new System.EventHandler(this.toolStripButton2_Click);
            // 
            // toolStripButton3
            // 
            this.toolStripButton3.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripButton3.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton3.Image")));
            this.toolStripButton3.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton3.Name = "toolStripButton3";
            this.toolStripButton3.Size = new System.Drawing.Size(60, 22);
            this.toolStripButton3.Text = "�����Ϣ";
            this.toolStripButton3.Click += new System.EventHandler(this.toolStripButton3_Click);
            // 
            // toolStripButton4
            // 
            this.toolStripButton4.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripButton4.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton4.Image")));
            this.toolStripButton4.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton4.Name = "toolStripButton4";
            this.toolStripButton4.Size = new System.Drawing.Size(60, 22);
            this.toolStripButton4.Text = "�ֹ�����";
            this.toolStripButton4.Click += new System.EventHandler(this.toolStripButton4_Click);
            // 
            // healthCheck
            // 
            this.healthCheck.Interval = 5000;
            this.healthCheck.Tick += new System.EventHandler(this.healthCheck_Tick);
            // 
            // Form1
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(298, 681);
            this.Controls.Add(this.toolStrip1);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "լ������Ϣ����";
            this.TopMost = true;
            this.Load += new System.EventHandler(this.Form1_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

		}
		#endregion

		/// <summary>
		/// Ӧ�ó��������ڵ㡣
		/// </summary>
		/// 
		[STAThread]

		[DllImport("sms.dll", EntryPoint="Sms_Connection")] 
����    public static extern uint Sms_Connection(string CopyRight,uint Com_Port,uint Com_BaudRate ,out string Mobile_Type,out string CopyRightToCOM); 

		[DllImport("sms.dll", EntryPoint="Sms_Disconnection")] 
����    public static extern uint Sms_Disconnection(); 

		[DllImport("sms.dll", EntryPoint="Sms_Send")] 
����    public static extern uint Sms_Send(string Sms_TelNum,string Sms_Text); 

		[DllImport("sms.dll", EntryPoint="Sms_Receive")] 
����    public static extern uint Sms_Receive(string Sms_Type,out string Sms_Text); 

		[DllImport("sms.dll", EntryPoint="Sms_Delete")] 
����    public static extern uint Sms_Delete(string Sms_Index); 

		[DllImport("sms.dll", EntryPoint="Sms_AutoFlag")] 
����    public static extern uint Sms_AutoFlag(); 

		[DllImport("sms.dll", EntryPoint="Sms_NewFlag")] 
����    public static extern uint Sms_NewFlag(); 

        _settings setting = new _settings();

        private uint port = 1;
        private uint baudrate = 9600;
        private string zhaileUrl = "http://www.fyzhaile.com/";

		static void Main() 
		{
			Application.Run(new Form1());
		}

        public string getCurrentTime()
        {
            return DateTime.Now.ToLongTimeString();
        }

        private bool loadSettings(){
            try 
	        {	        
        		this.port = uint.Parse(setting.readFromIni("ZHAILE","PORT"));    
	        }
	        catch (Exception)
	        {
                this.deviceStatus.Text = "��ȡ�����ļ�����";
                this.deviceStatus.ForeColor = Color.Red;
                return false;
	        }

            try 
	        {	        
        		this.baudrate = uint.Parse(setting.readFromIni("ZHAILE","BAUDRATE"));    
	        }
	        catch (Exception)
	        {
                this.deviceStatus.Text = "��ȡ�����ļ�����";
                this.deviceStatus.ForeColor = Color.Red;
                return false;
	        }

            try
            {
                this.zhaileUrl = setting.readFromIni("ZHAILE", "URL");
            }
            catch (Exception)
            {
                this.deviceStatus.Text = "��ȡ�����ļ�����";
                this.deviceStatus.ForeColor = Color.Red;
                return false;
            }
            return true;
        }

		private void Form1_Load(object sender, System.EventArgs e)
		{
            if (loadSettings())
            {
                connect();
            }
		}

		private bool connect()
		{

           String TypeStr="";
		   String CopyRightToCOM="";
           String CopyRightStr = "//�Ϻ�Ѹ����Ϣ�������޹�˾,��ַwww.xunsai.com//";

			if( Sms_Connection(CopyRightStr,this.port, this.baudrate,out TypeStr,out CopyRightToCOM) == 1) ///5Ϊ���ںţ�0Ϊ����ӿڣ�1,2,3,...Ϊ����
			{
                this.deviceStatus.Text = "�豸[" + TypeStr + " ]��������";
                this.deviceStatus.ForeColor = Color.Blue;
                this.taskTimer.Start();
                this.healthCheck.Start();
                return true;
			}
			else
			{
                this.deviceStatus.Text = "�豸�����쳣";
                this.deviceStatus.ForeColor = Color.Red;
                this.healthCheck.Start();
                return false;
			}
		}

        public bool checkNum(string num)
        {
            return Regex.IsMatch(num, @"^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$");
        }

        private void taskTimer_Tick(object sender, EventArgs e)
        {
            taskRun();
        }

        private void taskRun()
        {
            HttpClient client = new HttpClient();
            string taskUrl = this.zhaileUrl + "admin/json/querySmsTask.json";
            client.Url = taskUrl;
            this.groupBox2.Text = "��������...";
            try
            {
                string response = client.GetString();
                JArray ja = (JArray)JsonConvert.DeserializeObject(response);
                this.groupBox2.Text = "���յ�["+ja.Count+"]������...";
                this.networkStatus.Text = "�������ӳɹ�";
                this.networkStatus.ForeColor = Color.Blue;
                for (int i = 0; i < ja.Count; i++)
                {
                    string mobile = ja[i]["mobile"].ToString();
                    string text = ja[i]["text"].ToString();
                    string callback = ja[i]["callBackUrl"].ToString();
                    string status = sendMessage(mobile, text);
                    callBack(callback, status);
                }
            }
            catch
            {
                this.networkStatus.Text = "û�д���������";
                this.networkStatus.ForeColor = Color.Blue;
            }
            this.groupBox2.Text = "�������";
        }

        private string sendMessage(string mobile, string text)
        {
            if (text == null || text.Trim().Equals(""))
            {
                this.taskReport.Items.Add(getCurrentTime()+" - [" + mobile + "]��������Ϊ�ղ�����");
                return "3";
            }
            if (mobile == null || mobile.Trim().Equals("") || !checkNum(mobile))
            {
                this.taskReport.Items.Add(getCurrentTime() + " - [" + mobile + "]�޷�ʶ����ֻ�����");
                return "3";
            }
            this.groupBox2.Text = "["+mobile+"]�Ķ��ŷ�����...";
            uint result = Sms_Send(mobile, text);
            if (result == 1)
            {
                this.groupBox2.Text = "[" + mobile + "]�Ķ��ŷ��ͳɹ�...";
                this.taskReport.Items.Add(getCurrentTime() + " - [" + mobile + "]���ŷ��ͳɹ�");
                return "2";
            }
            else
            {
                this.groupBox2.Text = "[" + mobile + "]�Ķ��ŷ���ʧ��...";
                this.taskReport.Items.Add(getCurrentTime() + " - [" + mobile + "]���ŷ���ʧ��");
                return "3";
            }
        }

        private void callBack(string callback, string status)
        {
            this.groupBox2.Text = "���ڻظ�����״̬...";
            try
            {
                HttpClient client = new HttpClient();
                string taskUrl = this.zhaileUrl + callback.Replace("[status]", status);
                client.Url = taskUrl;
                string response = client.GetString();
                this.groupBox2.Text = "�ظ��������...";
                this.networkStatus.Text = "�������ӳɹ�";
                this.networkStatus.ForeColor = Color.Blue;
            }
            catch
            {
                this.groupBox2.Text = "�ظ�����ʧ��...";
                this.networkStatus.Text = "��������ʧ��";
                this.networkStatus.ForeColor = Color.Red;
            }
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            SysConfig config = new SysConfig();
            config.loadSettings();
            config.ShowDialog();
        }

        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            try
            {
                if (Sms_Disconnection() == 1)
                {
                    this.deviceStatus.Text = "�豸���ӶϿ��ɹ�";
                    this.deviceStatus.ForeColor = Color.Blue;
                }
                else
                {
                    this.deviceStatus.Text = "�豸���ӶϿ�ʧ��";
                    this.deviceStatus.ForeColor = Color.Red;
                }
            }
            catch
            {
                this.deviceStatus.Text = "�豸���ӶϿ�ʧ��";
                this.deviceStatus.ForeColor = Color.Red;
            }
            finally 
            {
               
                if (loadSettings())
                {
                    connect();
                }
            }
        }

        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            this.taskReport.Items.Clear();
        }

        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            taskRun();
        }

        private void healthCheck_Tick(object sender, EventArgs e)
        {
            if (taskTimer.Enabled)
            {
                this.groupBox1.Text = "ϵͳ������...";
            }
            else
            {
                this.groupBox1.Text = "ϵͳֹͣ������";
            }
        }

    }
}
