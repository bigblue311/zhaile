using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using SettingsAndLog4net;

namespace zhaile.mc
{
    public partial class SysConfig : Form
    {
        _settings setting = new _settings();

        public void loadSettings()
        {
            try
            {
                this.port.Text = setting.readFromIni("ZHAILE", "PORT");
                this.baudrate.Text = setting.readFromIni("ZHAILE", "BAUDRATE");
                this.zhaileUrl.Text = setting.readFromIni("ZHAILE", "URL");
            }
            catch (Exception)
            {
                MessageBox.Show("读取配置文件错误");
            }
        }

        public SysConfig()
        {
            InitializeComponent();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void Settings_Load(object sender, EventArgs e)
        {
            loadSettings();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                setting.WriteIni("ZHAILE", "PORT", this.port.Text);
                setting.WriteIni("ZHAILE", "BAUDRATE", this.baudrate.Text);
                setting.WriteIni("ZHAILE", "URL", this.zhaileUrl.Text);
            }
            catch (Exception)
            {
                MessageBox.Show("读取配置文件错误");
            }
            finally
            {
                this.Close();
            }
        }
    }
}