namespace java com.github.fhr.thrift.basic.service
namespace csharp com.github.fhr.thrift.basic.service

include "dto.thrift"

//服务
service DemoService {

   //用于检测client-server之间通讯是否正常
   string ping(),

   list<dto.Person> getPersonList(1:dto.QueryParameter parameter)

}