package main

import (
	"crypto/sha1"
	"encoding/hex"
	"strconv"
	"crypto/md5"
	"bytes"
	"fmt"
)

func  Sign(usid uint64, temStr string, timeValue int64, deId string) string{
	randSha1 := sha1.New()
	temStrSha1 := sha1.New()
	temStrSha1.Write(ToBytes(temStr))
	sha1ResHex := make([]byte, 40)
	hex.Encode(sha1ResHex, temStrSha1.Sum(nil))
	randSha1.Write(sha1ResHex)
	usidStr := strconv.FormatUint(usid, 10)
	randSha1.Write(ToBytes(usidStr))
	randSha1.Write([]byte{'\x65', '\x62', '\x72', '\x63', '\x55', '\x59', '\x69', '\x75', '\x78', '\x61', '\x5a', '\x76', '\x32', '\x58', '\x47', '\x75', '\x37', '\x4b', '\x49', '\x59', '\x4b', '\x78', '\x55', '\x72', '\x71', '\x66', '\x6e', '\x4f', '\x66', '\x70', '\x44', '\x46'})
	timeStr := strconv.FormatInt(timeValue, 10)
	randSha1.Write(ToBytes(timeStr))
	randSha1.Write(ToBytes(deId))
	hex.Encode(sha1ResHex, randSha1.Sum(nil))
	return ToString(sha1ResHex)
}

func Deid(feature string) string {
	m := md5.New()
	m.Write(ToBytes(feature))
	res := m.Sum(nil)
	resHex := make([]byte, 34)
	hex.Encode(resHex[2:], res)
	resHex[0] = 'O'
	resHex[1] = '|'
	return ToString(bytes.ToUpper(resHex))
}

func ToString(value []byte) string  {
	return string(value)
}

func ToBytes(value string) []byte  {
	return []byte(value)
}

func main(){
	var usid uint64 =1
	var temStr = "hello"
	//var time = time.Now().Second()
	var timeValue int64 = 1
	//var deId = Deid("helloworld")
	var deId = "O|FC5E038D38A57032085441E7FE7010B0";
	fmt.Println(Sign(usid, temStr, timeValue, deId))
	//fmt.Println(Deid("helloworld"))
}