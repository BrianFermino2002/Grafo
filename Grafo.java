/*
Authors:
BRIAN JEFFREY FERMINO - 2318560-1
DANIEL CIRILO – 2322249-2
HARISON SILVA – 2301719-8
MARCEL BARRETO – 2288386-0
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grafo {

    private int adjMatrix[][];
    private LinkedList<Integer> adj[];
    private int numVertices;
    LinkedList<Integer> val[];

    // Construir matriz e Lista
    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new int[numVertices][numVertices];
        adj = new LinkedList[numVertices];
        val = new LinkedList[adjMatrix.length];
        for (int i = 0; i < numVertices; ++i) {
            adj[i] = new LinkedList();
        }
        for (int i = 0; i < numVertices; ++i) {
            val[i] = new LinkedList();
        }
    }

    // Adicionar arestas
    public void addEdge(int i, int j) {
        if(adjMatrix[i][j]>=1 && adjMatrix[j][i] >= 1){
            adjMatrix[i][j] += 1;
            adjMatrix[j][i] += 1;
        }else{
        adjMatrix[i][j] = 1;
        adjMatrix[j][i] = 1;
        adj[i].add(j);
        adj[j].add(i);
        }
    }

    // Mostrar matriz
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("    ");
        for (int i = 0; i < numVertices; i++) {
            s.append(i + 1);
            s.append(" ");
        }
        s.append("\n");
        for (int i = 0; i < numVertices; i++) {
            if (i < 9) {
                s.append(i + 1 + " : ");
            } else {
                s.append(i + 1 + ": ");
            }
            for (int j : adjMatrix[i]) {

                s.append((j > 0 ? j : 0) + " ");

            }
            s.append("\n");
        }
        return s.toString();
    }

    //Grau Maximo e Mínimo
    public void grauMaxMin() {
        int maiores[] = new int[adjMatrix.length];
        int menores[] = new int[adjMatrix.length];
        int grauMax = 0, grauMin = 100;
        int soma = 0, vezes = 0;
        for (int i = 0; i < adjMatrix.length; i++) {
            soma = 0;
            for (int j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j]>0) {
                    soma += adjMatrix[i][j];
                }
            }
            val[i].add(soma);
        }
        for (int i = 0; i < val.length; i++) {
            if (grauMax < val[i].get(0)) {
                grauMax = val[i].get(0);
            } else if (grauMin > val[i].get(0)) {
                grauMin = val[i].get(0);
            }
        }

        for (int i = 0; i < val.length; i++) {
            if (val[i].get(0) == grauMax) {
                maiores[i] = i + 1;
            } else if (val[i].get(0) == grauMin) {
                menores[i] = i + 1;
            }
        }
        System.out.print("\nO maior grau é " + grauMax + " e está presente nos vértices:\n");
        for (int i = 0; i < maiores.length; i++) {
            if (maiores[i] != 0) {
                System.out.print(maiores[i] + " ");
            }
        }
        System.out.print("\n\nO menor grau é " + grauMin + " e está presente nos vértices:\n");
        for (int i = 0; i < menores.length; i++) {
            if (menores[i] != 0) {
                System.out.print(menores[i] + " ");
            }
        }
    }

        // Busca em Largura
    public void BFS(int start, int finish) {
        boolean buscado = false;
        // Array para guardar vértices ja visitados
        boolean[] visited = new boolean[adj.length];
        //Contém o valor do vértice que introduziu vértice
        int[] parent = new int[adj.length];
        Queue<Integer> q = new LinkedList<>();

        q.add(start);
        parent[start] = -1;
        visited[start] = true;
        System.out.print((start + 1) + "->");
        while (!q.isEmpty()) {
            // Colocar o primeiro vértice na Fila
            int current = q.poll();
            // Se o vértice removido da fila for o procurado, saia do loop
            if (current == finish) {
                break;
            }

            for (int neighbour : adj[current]) {
                // Se o filho não foi visitado
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    // adicionar na fila
                    q.add(neighbour);
                    parent[neighbour] = current;
                    if (!buscado) {
                        if (neighbour != finish) {
                            System.out.print((neighbour + 1) + " ->");
                        }
                        if (neighbour == finish) {
                            buscado = true;
                            break;
                        }
                    }
                }
            }
        }
        if (buscado) {
            System.out.println(finish + 1);
        }else{
            System.out.println("\n Não foi possivel encontrar o Vértice informado á partir desse começo");
        }
    }

    void DFSUtil(int v, boolean[] visited) {
        // Mark the current node as visited and print it
        visited[v] = true;
        System.out.print((v + 1) + " ");
        // Recur for all the vertices
        // adjacent to this vertex
        for (int x : adj[v]) {
            if (!visited[x]) {
                DFSUtil(x, visited);
            }
        }
    }

    void connectedComponents() {
        int soma = 0;
        // Marcar todos os vértices como não visitados
        boolean[] visited = new boolean[numVertices];
        for (int v = 0; v < numVertices; ++v) {

            if (!visited[v]) {
                // print all reachable vertices
                // from v
                System.out.print("Componente " + (soma + 1) + ": ");
                DFSUtil(v, visited);
                System.out.println();
                soma++;
            }
        }
    }

    public static void main(String args[]) throws IOException {
        //lendo o arquivo 
        Scanner a = new Scanner(System.in);
        System.out.println("Digite o caminho até o arquivo .txt (exemplo: C:\\Users\\T-Gamer\\Desktop/arquivo.txt): ");
        String path = a.next();
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        //quantidade de vértices
        int qtd_vertice = Integer.parseInt(buffRead.readLine());
        Grafo g = new Grafo(qtd_vertice);

        //capturando quantidade de linhas e as linhas do arquivo
        int l = 0;
        String[] linhas = new String[100];
        while (buffRead.ready()) {
            linhas[l] = buffRead.readLine();
            l++;
        }

        //construir a matriz de acordo com o usuário
        int cont = 0;
        int arestas = 0;
        String vetor[] = new String[2];

        for (int i = 0; i < l; i++) {
            vetor = linhas[(l - 1) - cont].split(" ");
            if (vetor.length > 1) {
                int val1 = Integer.parseInt(vetor[0]);
                int val2 = Integer.parseInt(vetor[1]);

                g.addEdge(val1 - 1, val2 - 1);
                cont++;
                arestas++;
            } else {
                cont++;
            }
        }
        //mostrando a matriz
        System.out.print(g.toString());

        //informações
        System.out.println("\n\nNúmero de vértices: " + qtd_vertice);
        System.out.println("\nNúmero de arestas: " + arestas);

        //Grau Mínimo e Máximo
        g.grauMaxMin();

        
        //Capturando informações para a BFS
        System.out.println("\n\nDigite o vértice de Início para a busca BFS: ");
        int inicio = a.nextInt();
        System.out.println("Digite o vértice de Fim para a busca BFS: ");
        int fim = a.nextInt();

        g.BFS(inicio - 1, fim - 1);
        System.out.println("\n");

        System.out.print("Componentes conexos:\n");
        g.connectedComponents();

    }
}
