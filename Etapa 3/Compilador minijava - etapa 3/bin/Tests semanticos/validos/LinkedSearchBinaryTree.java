class LinkedBinaryTree{
	varinst Node _root;
	varinst int _size;

	LinkedBinaryTree(){
		(LinkedBinaryTree_init());
	}
	dynamic void LinkedBinaryTree_init(){
		_root = null;
		_size= 0;
	}	

	dynamic int size(){
		return _size;
	}

	dynamic boolean isEmpty(){
		return _size == 0;	
	}

	dynamic Entry replace(Node node, Entry e)
	
	{
		varlocal Entry toReturn;
		
		toReturn = node.getEntry();
		(node.setEntry(e));
		return toReturn;
	}

	dynamic Node root(){
		if ((isEmpty()))
			return null;
		
		return _root;
	}
	
	dynamic Node parent(Node n){
		if (n == _root)
			return null;

		return (n.getParent());
	}

	dynamic boolean isInternal(Node node){
		return (hasLeft(node)) || (hasRight(node));
	}
	
	dynamic boolean isExternal(Node node){
		return !(isInternal(node));
	}

	dynamic boolean isRoot(Node node){
		return node == _root;
	}

	dynamic void setRoot(Entry entry){
		if ((isEmpty())){
			_root = new Node(entry);
			_size = _size +1;
		}else
			(_root.setEntry(entry));
	}
	dynamic Node addLeft(Node padre, Entry entry)
	
	{	varlocal Node nuevo;
		if ((hasLeft(padre)))
			return null;
		
		nuevo = new Node(entry);
		(nuevo.setParent(padre));
		(padre.setLeft(nuevo));
		_size = _size+1;
		
		return nuevo;
	}

	dynamic Node addRight(Node padre, Entry entry)
	
	{	varlocal Node nuevo;
		if ((hasRight(padre)))
			return null;
		
		nuevo = new Node(entry);
		(nuevo.setParent(padre));
		(padre.setRight(nuevo));
		_size = _size+1;
		
		return nuevo;
	}

	dynamic Node left(Node node){
		if((isExternal(node)))
			return null;

		return (node.getLeft());
	}
	dynamic Node right(Node node){
		if((isExternal(node)))
			return null;

		return (node.getRight());
	}
	dynamic boolean hasLeft(Node node){
		return (node.getLeft()) != null;
	}

	dynamic boolean hasRight(Node node){
		return (node.getRight()) != null;
	}
	
}


class LinkedSearchBinaryTree extends LinkedBinaryTree {


	LinkedSearchBinaryTree(){
		(LinkedBinaryTree_init());
	}
	
	dynamic Entry find(int key)
	
	{	varlocal Node node;
		if (! isEmpty()){
			node = treeSearch(key,root());
			return node.getEntry();
		}else
			return null;
	} 
	
	dynamic Entry insert(int key, String value)
	
	{	varlocal Node left_a, right_a;
		
		if (!isEmpty()){
			return treeInsert(key, value , root());
		}else{
			(setRoot( new Entry(key,value)));
			return root().getEntry();
		}	
	}

	dynamic Entry remove(int key)
	
	{
		varlocal Node w;
		varlocal Entry min;
		
		if (isEmpty())
			return null;
		
		w = treeSearch(key,root());
		if (! isExternal(w)){
			if (isExternal(left(w)))
				(removeExternal(left(w)));
			else if (isExternal(right(w)))
				(removeExternal(right(w)));
			else{
				min = removeMin(w);
				(replace(w,min));
			}
		}
		return w.getEntry();
		
	}
	
	dynamic Node treeSearch(int key,Node nodo){
		if (isExternal(nodo))
			return nodo;
		
		if (key < nodo.getEntry().getKey())
			return treeSearch(key, nodo.getLeft());
		else if (key > nodo.getEntry().getKey())
			return treeSearch(key, nodo.getRight());
		else
			return nodo;
	}
	
	dynamic Entry treeInsert(int key, String value, Node nodo)
	
	{	varlocal Node toReturn;
		toReturn = treeSearch(key,nodo);
		if (isExternal(toReturn))
			return insertExternalNode(key,value,toReturn);
		else
			return null;
	}
	
	dynamic Entry insertExternalNode(int key, String value, Node nodo){
		(nodo.setEntry(new Entry(key,value)));
		(addLeft(nodo,null));
		(addRight(nodo,null));
		return nodo.getEntry();
	}
	
	dynamic void removeExternal(Node nodo)
	
	{	varlocal Node padre,hermano,padreNuevo;
		if (isExternal(nodo)){
			padre = parent(nodo);
			hermano = padre.getRight();
			padreNuevo = padre.getParent();
			
			if (padreNuevo.getLeft() == padre)
				(padreNuevo.setLeft(hermano));
			else if (padreNuevo.getRight() == padre)
				(padreNuevo.setRight(hermano));
			
			(hermano.setParent(padreNuevo));
		}
	}
	
	dynamic Entry removeMin(Node nodo)
	
	{	varlocal Node v;
		v = right(nodo);
		while (isInternal(left(v))){
			v = left(v);
		}
		(removeExternal(left(v)));
		return v.getEntry();
	}
}

class Node{
	varinst Entry entry;
	varinst Node left, right,parent;

	Node(Entry e){
		(setEntry(e));
		(setLeft(null));
		(setRight(null));
		(setParent(null));
	}
	dynamic void setParent(Node n){
		parent = n;
	}
	dynamic void setLeft(Node n){
		left = n;
	}
	dynamic void setRight(Node n){
		right = n;
	}
	dynamic void setEntry(Entry e){
		entry = e;
	}	
	dynamic Node getParent(){
		return parent;
	}
	dynamic Entry getEntry(){
		return entry;
	}
	dynamic Node getLeft(){
		return left;
	}
	dynamic Node getRight(){
		return right;
	}
}

class Entry{
	varinst String elem;
	varinst int key;

	Entry(int k, String e){
		key = k;
		elem = e;
	}
	dynamic int getKey(){
		return key;	
	}
	dynamic String getElement(){
		return elem;	
	}
	dynamic void setElement(String e){
		elem = e;
	}

}

class Main{
	static void insert(LinkedSearchBinaryTree t, int key, String value){
		(t.insert(key,value));
		(System.printI(key));
		(System.printS(" -> "));
		(System.printS(value));
		(System.println());
	}
	static void obtener(LinkedSearchBinaryTree t, int key)
		
	{	varlocal Entry e;
		
		(System.printS("Obtener: "));
		(System.printI(key));
		(System.printS(" > "));
		e = t.find(key);
		if (e != null)
			(System.printS(e.getElement()));
		else
			(System.printS("No se ha encontrado."));
		(System.println());
	}
	static void eliminar(LinkedSearchBinaryTree t,int key){
		(System.printS("Eliminar "));
		(System.printI(key));
		(System.printS("."));
		(System.println());
		(t.remove(key));
	}
	
	static void main()
	
	{	varlocal LinkedSearchBinaryTree t;
		(System.printSln("Se insertaran los siguientes animales: "));
		t = new LinkedSearchBinaryTree();
		(insert(t,15,"Oso"));
		(insert(t,45,"Perro"));
		(insert(t,56,"Elefante"));
		(insert(t,1,"Koala"));
		(insert(t,12,"Leon"));
		(insert(t,543,"Tigre"));
		(insert(t,156,"Gato"));
		(insert(t,34,"Leopardo"));
		(insert(t,26,"Loro"));
		(insert(t,11,"Tucan"));
		(insert(t,100,"Pollo"));
		(insert(t,320,"Caballo"));
		(insert(t,800,"Vaca"));
		(insert(t,810,"Toro"));
		(insert(t,901,"Ardilla"));
		(insert(t,43,"Coyote"));
		(insert(t,2,"Lobo"));
		
		
		(System.println());
		
		(obtener(t,543));
		(obtener(t,11));
		(obtener(t,2));
		(obtener(t,810));
		(obtener(t,901));
		
		(System.println());
		(eliminar(t,543));
		(eliminar(t,320));
		(eliminar(t,901));
		(System.println());
		
		(obtener(t,543));
		(obtener(t,901));
		(obtener(t,320));
		(obtener(t,43));
		(obtener(t,12));
		
	}
}