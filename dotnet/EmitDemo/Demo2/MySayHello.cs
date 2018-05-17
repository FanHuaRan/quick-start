using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Demo2
{
    public class MySayHello : ISayHello
    {
        public string SayHello(string name)
        {
                Console.WriteLine("Hello {0}", name);
                return "Hello " + name;
        }
    }
}
