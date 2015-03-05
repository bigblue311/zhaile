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
	/// Form1 的摘要说明。
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
			// Windows 窗体设计器支持所必需的
			//
			InitializeComponent();

			//
			// TODO: 在 InitializeComponent 调用后添加任何构造函数代码
			//
		}

		/// <summary>
		/// 清理所有正在使用的资源。
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
		/// 设计器支持所需的方法 - 不要使用代码编辑器修改
		/// 此方法的内容。
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
            this.groupBox1.Text = "系统状态";
            // 
            // networkStatus
            // 
            this.networkStatus.AutoSize = true;
            this.networkStatus.ForeColor = System.Drawing.Color.Blue;
            this.networkStatus.Location = new System.Drawing.Point(67, 42);
            this.networkStatus.Name = "networkStatus";
            this.networkStatus.Size = new System.Drawing.Size(83, 12);
            this.networkStatus.TabIndex = 3;
            this.networkStatus.Text = "网络连接中...";
            // 
            // deviceStatus
            // 
            this.deviceStatus.AutoSize = true;
            this.deviceStatus.ForeColor = System.Drawing.Color.Red;
            this.deviceStatus.Location = new System.Drawing.Point(67, 21);
            this.deviceStatus.Name = "deviceStatus";
            this.deviceStatus.Size = new System.Drawing.Size(53, 12);
            this.deviceStatus.TabIndex = 2;
            this.deviceStatus.Text = "无法连接";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(7, 42);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(65, 12);
            this.label2.TabIndex = 1;
            this.label2.Text = "网络连接：";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(7, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(65, 12);
            this.label1.TabIndex = 0;
            this.label1.Text = "设备连接：";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.taskReport);
            this.groupBox2.Location = new System.Drawing.Point(4, 102);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(290, 579);
            this.groupBox2.TabIndex = 1;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "消息任务";
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
            this.toolStripButton1.Text = "系统配置";
            this.toolStripButton1.Click += new System.EventHandler(this.toolStripButton1_Click);
            // 
            // toolStripButton2
            // 
            this.toolStripButton2.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripButton2.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton2.Image")));
            this.toolStripButton2.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton2.Name = "toolStripButton2";
            this.toolStripButton2.Size = new System.Drawing.Size(60, 22);
            this.toolStripButton2.Text = "重新连接";
            this.toolStripButton2.Click += new System.EventHandler(this.toolStripButton2_Click);
            // 
            // toolStripButton3
            // 
            this.toolStripButton3.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripButton3.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton3.Image")));
            this.toolStripButton3.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton3.Name = "toolStripButton3";
            this.toolStripButton3.Size = new System.Drawing.Size(60, 22);
            this.toolStripButton3.Text = "清除消息";
            this.toolStripButton3.Click += new System.EventHandler(this.toolStripButton3_Click);
            // 
            // toolStripButton4
            // 
            this.toolStripButton4.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripButton4.Image = ((System.Drawing.Image)(resources.GetObject("toolStripButton4.Image")));
            this.toolStripButton4.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.toolStripButton4.Name = "toolStripButton4";
            this.toolStripButton4.Size = new System.Drawing.Size(60, 22);
            this.toolStripButton4.Text = "手工触发";
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
            this.Text = "宅乐网消息中心";
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
		/// 应用程序的主入口点。
		/// </summary>
		/// 
		[STAThread]

		[DllImport("sms.dll", EntryPoint="Sms_Connection")] 
　　    public static extern uint Sms_Connection(string CopyRight,uint Com_Port,uint Com_BaudRate ,out string Mobile_Type,out string CopyRightToCOM); 

		[DllImport("sms.dll", EntryPoint="Sms_Disconnection")] 
　　    public static extern uint Sms_Disconnection(); 

		[DllImport("sms.dll", EntryPoint="Sms_Send")] 
　　    public static extern uint Sms_Send(string Sms_TelNum,string Sms_Text); 

		[DllImport("sms.dll", EntryPoint="Sms_Receive")] 
　　    public static extern uint Sms_Receive(string Sms_Type,out string Sms_Text); 

		[DllImport("sms.dll", EntryPoint="Sms_Delete")] 
　　    public static extern uint Sms_Delete(string Sms_Index); 

		[DllImport("sms.dll", EntryPoint="Sms_AutoFlag")] 
　　    public static extern uint Sms_AutoFlag(); 

		[DllImport("sms.dll", EntryPoint="Sms_NewFlag")] 
　　    public static extern uint Sms_NewFlag(); 

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
                this.deviceStatus.Text = "读取配置文件错误";
                this.deviceStatus.ForeColor = Color.Red;
                return false;
	        }

            try 
	        {	        
        		this.baudrate = uint.Parse(setting.readFromIni("ZHAILE","BAUDRATE"));    
	        }
	        catch (Exception)
	        {
                this.deviceStatus.Text = "读取配置文件错误";
                this.deviceStatus.ForeColor = Color.Red;
                return false;
	        }

            try
            {
                this.zhaileUrl = setting.readFromIni("ZHAILE", "URL");
            }
            catch (Exception)
            {
                this.deviceStatus.Text = "读取配置文件错误";
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
           String CopyRightStr = "//上海迅赛信息技术有限公司,网址www.xunsai.com//";

			if( Sms_Connection(CopyRightStr,this.port, this.baudrate,out TypeStr,out CopyRightToCOM) == 1) ///5为串口号，0为红外接口，1,2,3,...为串口
			{
                this.deviceStatus.Text = "设备[" + TypeStr + " ]连接正常";
                this.deviceStatus.ForeColor = Color.Blue;
                this.taskTimer.Start();
                this.healthCheck.Start();
                return true;
			}
			else
			{
                this.deviceStatus.Text = "设备连接异常";
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
            this.groupBox2.Text = "正在连接...";
            try
            {
                string response = client.GetString();
                JArray ja = (JArray)JsonConvert.DeserializeObject(response);
                this.groupBox2.Text = "接收到["+ja.Count+"]个任务...";
                this.networkStatus.Text = "网络连接成功";
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
                this.networkStatus.Text = "没有待处理任务";
                this.networkStatus.ForeColor = Color.Blue;
            }
            this.groupBox2.Text = "任务完成";
        }

        private string sendMessage(string mobile, string text)
        {
            if (text == null || text.Trim().Equals(""))
            {
                this.taskReport.Items.Add(getCurrentTime()+" - [" + mobile + "]短信内容为空不发送");
                return "3";
            }
            if (mobile == null || mobile.Trim().Equals("") || !checkNum(mobile))
            {
                this.taskReport.Items.Add(getCurrentTime() + " - [" + mobile + "]无法识别的手机号码");
                return "3";
            }
            this.groupBox2.Text = "["+mobile+"]的短信发送中...";
            uint result = Sms_Send(mobile, text);
            if (result == 1)
            {
                this.groupBox2.Text = "[" + mobile + "]的短信发送成功...";
                this.taskReport.Items.Add(getCurrentTime() + " - [" + mobile + "]短信发送成功");
                return "2";
            }
            else
            {
                this.groupBox2.Text = "[" + mobile + "]的短信发送失败...";
                this.taskReport.Items.Add(getCurrentTime() + " - [" + mobile + "]短信发送失败");
                return "3";
            }
        }

        private void callBack(string callback, string status)
        {
            this.groupBox2.Text = "正在回复任务状态...";
            try
            {
                HttpClient client = new HttpClient();
                string taskUrl = this.zhaileUrl + callback.Replace("[status]", status);
                client.Url = taskUrl;
                string response = client.GetString();
                this.groupBox2.Text = "回复任务完成...";
                this.networkStatus.Text = "网络连接成功";
                this.networkStatus.ForeColor = Color.Blue;
            }
            catch
            {
                this.groupBox2.Text = "回复任务失败...";
                this.networkStatus.Text = "网络连接失败";
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
                    this.deviceStatus.Text = "设备连接断开成功";
                    this.deviceStatus.ForeColor = Color.Blue;
                }
                else
                {
                    this.deviceStatus.Text = "设备连接断开失败";
                    this.deviceStatus.ForeColor = Color.Red;
                }
            }
            catch
            {
                this.deviceStatus.Text = "设备连接断开失败";
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
                this.groupBox1.Text = "系统运行中...";
            }
            else
            {
                this.groupBox1.Text = "系统停止运行了";
            }
        }

    }
}
