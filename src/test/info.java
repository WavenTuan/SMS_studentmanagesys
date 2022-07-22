package test;

public class info {
	/*因为刚开始没有考虑到测试需求，所以这个test文件夹实际是空的。（sorry
	  实际测试内容为包cn.com.dhw.sms.db中的CreateTestTable类和InsertAndSelect类。
	  单独以java_application方式顺序运行这两个程序进行数据库连接测试。（右键单机他们，然后选择run as java_application
	  如果连接成功，则会在后台看到SQL库中看到新建了一个sms库，里面存在一张test_table表，插入了4条包含“段惠文”字符的数据。
	  如果连接失败，应该会报各种错误。
	  
	  注意事项：
	  1.在测试之前请在本机服务中开启SQL并登录（默认服务端口3306）and 所使用的sql应该支持中文字符。
	  1.1(没有做任何别的数据库的适应，所以各种别的先进的数据库都无法支持）QAQ！
	  2.VERY IMPORTANT:因为没有把程序代码和测试代码彻底隔离，在eclipse中直接按工具栏中的RUN可能会只运行测试java程序
	  所以请右键单机cn.com.dhw.sms选择run as-->eclipse application，则可以正常运行。
	  3.建议在run之前设置run config，勾选 clear中的workspace，防止缓存数据影响运行结果
	  ------by 段惠文 dhw18@mails.tsinghua.edu.cn*/
}
