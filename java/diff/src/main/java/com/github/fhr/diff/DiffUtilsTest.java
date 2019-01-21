package com.github.fhr.diff;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Fan Huaran
 * created on 2019/1/21
 * @description
 */
public class DiffUtilsTest {
    public static void main(String[] args) throws PatchFailedException {
        List<String> original = Arrays.asList("abc", "def", "gh");
        List<String> revised = Arrays.asList("abc", "def", "hij", "lmn");
        // 比较两文本的diff，并返回patch，patch记录了两文本的不同和相同点
        Patch<String> patch = DiffUtils.diff(original, revised);
        for (Delta<String> delta : patch.getDeltas()) {
            System.out.printf("original:%s revised:%s type:%s", delta.getOriginal(), delta.getRevised(), delta.getType());
        }

        // 使用patch将源文件转换为目标文本
        assert revised.equals(DiffUtils.patch(original, patch));

        // 使用patch将目标文本反转换为源文本
        assert original.equals(DiffUtils.unpatch(revised, patch));
    }
}
