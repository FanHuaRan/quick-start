using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Reflection.Emit;
using System.Text;
using System.Threading.Tasks;

namespace Demo2
{
    class Program
    {
        private static readonly String SAVE_DOMAIN_NAME = "MyDynamic.dll";

        static void Main(string[] args)
        {
            // 获取当前的应用程序域
            var appDomain = AppDomain.CurrentDomain;

            // 动态在当前的AppDomain当中创建一个程序集 
            var assemblyName = new AssemblyName("MyDynamicAssembly");
            // 该程序集的访问模式设置为run,意味着该程序集只驻留在内存，不会持久化
             var assemblyBuilder = appDomain.DefineDynamicAssembly(assemblyName, AssemblyBuilderAccess.Run);
            // 该程序集的访问模式设置为runAndSave,意味着该程序集既可驻留内存,又可以持久化
            //var assemblyBuilder = appDomain.DefineDynamicAssembly(assemblyName, AssemblyBuilderAccess.RunAndSave);

            // 动态在程序集内创建一个模块
            var moduleBuilder = assemblyBuilder.DefineDynamicModule("MyDynamicModule");
            // 动态在程序集内创建一个模块，RunAndSave模式下，后面需要指定保存的文件名
            //var moduleBuilder = assemblyBuilder.DefineDynamicModule("MyDynamicModule",SAVE_DOMAIN_NAME);

            // 动态在模块内创建一个类
            var typeBuilder = moduleBuilder.DefineType("MyDynamicType", TypeAttributes.Public);

            // 该类实现ISayHello接口 ,实现接口需要遵守可访问性的原则
            typeBuilder.AddInterfaceImplementation(typeof(ISayHello));

            // 定义一个方法，该方法实际上就是实现接口的SayHello方法
            var returnType = typeof(String);
            Type[] paramTypes = {typeof(String)};
            var methodBuilder = typeBuilder.DefineMethod("SayHello", MethodAttributes.Public | MethodAttributes.Virtual,
                returnType, paramTypes);

            // 使用ILGenerator为方法进行实现
            var ilGenerator = methodBuilder.GetILGenerator();
            /** 全部需要使用IL中间代码来编写 **/

            // 如果修改操作码，则填补空间
            ilGenerator.Emit(OpCodes.Nop);
            // 定义一个字符串常量到栈顶
            ilGenerator.Emit(OpCodes.Ldstr, "Hello {0}");
            // 将索引为1的变量加到栈顶
            ilGenerator.Emit(OpCodes.Ldarg_1);
            //调用Console.WriteLine方法，参数就是当前栈顶的两个参数
            ilGenerator.Emit(OpCodes.Call, (typeof(Console)).GetMethod("WriteLine", new Type[] { typeof(string),typeof(Object) }));
            // 如果修改操作码，则填补空间
            ilGenerator.Emit(OpCodes.Nop);
            // 定义一个字符串常量到栈顶
            ilGenerator.Emit(OpCodes.Ldstr, "Hello ");
            // 将索引为1的变量加到栈顶
            ilGenerator.Emit(OpCodes.Ldarg_1);
            // 调用String.concat方法，参数就是当前栈顶的两个参数
            ilGenerator.Emit(OpCodes.Call, (typeof(String)).GetMethod("Concat", new Type[] { typeof(string), typeof(string) }));
            // 栈顶存储着拼接结果，将其弹出`
            ilGenerator.Emit(OpCodes.Stloc_0);
            // 定义一个label（指令流中的一条指令位置）
            Label targetInstruction = ilGenerator.DefineLabel();
            // 无条件地将控制转移到targetInstruction
            ilGenerator.Emit(OpCodes.Br_S, targetInstruction);
            // targetInstruction标记为当前流所在位置
            ilGenerator.MarkLabel(targetInstruction);
            // 将索引为0的变量加到栈顶
            ilGenerator.Emit(OpCodes.Ldloc_0);
            // 方法返回，并且栈顶的参数作为返回值返回
            ilGenerator.Emit(OpCodes.Ret);

            // 正式创建类
            var dynamicType = typeBuilder.CreateType();

            // RunAndSave模式下可保存该程序集，方便调试
            //assemblyBuilder.Save(SAVE_DOMAIN_NAME);

            // 反射创建对象
            var dynamicObj = Activator.CreateInstance(dynamicType);
            // 通过接口来使用该对象
            ISayHello sayHello = dynamicObj as ISayHello;
            sayHello.SayHello(".NET");

            Console.ReadLine();
        }
    }
}
