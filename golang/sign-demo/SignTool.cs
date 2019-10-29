using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace APP
{
    /// <summary>
    /// 
    /// </summary>
    class MySign
    {
        public Int64 Uid { get; set; }
        public Int64 Time { get; set; }
        public String DevUID { get; set; }
    }

    class Helper
    {
        public static string Md5(string str)
        {
            MD5 md5 = MD5.Create();
            byte[] resultBytes = md5.ComputeHash(Encoding.UTF8.GetBytes(str));
            return HexEncode(resultBytes);
        }

        public static String HexEncode(byte[] input)
        {
            StringBuilder tmp = new StringBuilder();
            foreach (byte i in input)
            {
                tmp.Append(i.ToString("x2"));
            }
            return tmp.ToString();
        }

        public static byte[] HexEncode4Bytes(byte[] input)
        {
            return Encoding.UTF8.GetBytes(HexEncode(input));
        }

        public static byte[] MergeBytes(byte[][] bytesArray)
        {
            var length = 0;
            foreach (byte[] value in bytesArray)
            {
                length += value.Length;
            }
            byte[] mergeBytes = new byte[length];

            int index = 0;
            foreach (byte[] value in bytesArray)
            {
                value.CopyTo(mergeBytes, index);
                index += value.Length;
            }

            return mergeBytes;
        }
    }

    class SignTool
    {
        private static byte[] CONSTANT_BYTES = { 0x65, 0x62, 0x72, 0x63, 0x55, 0x59, 0x69, 0x75, 0x78, 0x61, 0x5a, 0x76, 0x32, 0x58, 0x47, 0x75, 0x37, 0x4b, 0x49, 0x59, 0x4b, 0x78, 0x55, 0x72, 0x71, 0x66, 0x6e, 0x4f, 0x66, 0x70, 0x44, 0x46 };

        public static String Sign(MySign mySign, string tempStr)
        {
            var sha1CryptoServiceProvider = new SHA1CryptoServiceProvider();
            byte[] tempStrSha1Bytes = sha1CryptoServiceProvider.ComputeHash(Encoding.UTF8.GetBytes(tempStr));

            byte[] tempStrSha1HexBytes = Helper.HexEncode4Bytes(tempStrSha1Bytes);
            byte[] uidBytes = Encoding.UTF8.GetBytes(mySign.Uid.ToString());
            byte[] timeBytes = Encoding.UTF8.GetBytes(mySign.Time.ToString());
            byte[] devUIDBytes = Encoding.UTF8.GetBytes(mySign.DevUID);

            byte[] mergeBytes = Helper.MergeBytes(new byte[][] { tempStrSha1HexBytes, uidBytes, CONSTANT_BYTES, timeBytes, devUIDBytes });

            byte[] resultSha1Bytes = sha1CryptoServiceProvider.ComputeHash(mergeBytes);
            return Helper.HexEncode(resultSha1Bytes);
        }

        public static string getDevUID(String feature)
        {
            return "O|" + Helper.Md5(feature).ToUpper();
        }
    }

    class APP
    {
        static void Main(string[] args)
        {
            //var rightValue = "O|FC5E038D38A57032085441E7FE7010B0";
            //var testValue = SignTool.getDevUID("helloworld");
            //Console.WriteLine(rightValue == testValue);
            //Console.WriteLine(testValue);

            MySign mySign = new MySign();
            mySign.Uid = 1L;
            mySign.DevUID = SignTool.getDevUID("helloworld");
            mySign.Time = 1L;
            var outSign = SignTool.Sign(mySign, "hello");
            Console.WriteLine(outSign);
            Console.ReadKey();
        }
    }
}
