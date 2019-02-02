package cn.tanlw.smallapp.tensorflow;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * https://www.ioiogoo.cn/2018/04/03/java%E8%B0%83%E7%94%A8keras%E3%80%81tensorflow%E6%A8%A1%E5%9E%8B/
 */
public class DemoImportGraph2 {

    public static void main(String[] args) throws IOException {
        float[][] input = new float[1][80];
        input[0] = new float[]{0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 1f, 1f, 0f, 0f, 0f, 0f, 1f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 0f, 0f, 0f, 0f};

        try (Graph graph = new Graph()) {
            graph.importGraphDef(Files.readAllBytes(Paths.get(
                    "D:\\DATA\\rec_neural_net_v1.pb"
            )));

            try (Session sess = new Session(graph)) {
                try (Tensor x = Tensor.create(input);
                     // input是输入的name，output是输出的name
                     Tensor y = sess.runner().feed("x", x).fetch("output").run().get(0)) {
                    float[][] result = new float[1][Integer.parseInt(y.shape()[1] + "")];
                    y.copyTo(result);
                    System.out.println(Arrays.toString(y.shape()));
                    System.out.println(Arrays.toString(result[0]));
                }
            }
        }

    }
}

