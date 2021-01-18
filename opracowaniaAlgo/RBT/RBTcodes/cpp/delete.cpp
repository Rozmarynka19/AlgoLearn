void Delete(T data)
{
   Node<T>* X = Find(data);
   Node<T> * W, * Y, * Z;

  if( ( X->left == &blackSentinelNode ) || ( X->right == &blackSentinelNode ) ) Y = X;
  else Y = getSuccessor ( X );

  if( Y->left != &blackSentinelNode ) Z = Y->left;
  else Z = Y->right;

  Z->parent = Y->parent;

  if( Y->parent == &blackSentinelNode ) root = Z;
  else if( Y == Y->parent->left ) Y->parent->left  = Z;
  else Y->parent->right = Z;

  if( Y != X ) X->data = Y->data;

  if( Y->isBlack)
    while( ( Z != root ) && ( Z->isBlack) )
      if( Z == Z->parent->left )
      {
        W = Z->parent->right;

        if( !W->isBlack )
        {
          W->isBlack = true;
          Z->parent->isBlack = false;
          rotateLeft ( Z->parent, Z->parent->parent );
          W = Z->parent->right;
        }

        if( ( W->left->isBlack) && ( W->right->isBlack) )
        {
          W->isBlack = false;
          Z = Z->parent;
          continue;
        }

        if( W->right->isBlack)
        {
          W->left->isBlack = true;
          W->isBlack = false;
          rotateRight ( W, W->parent );
          W = Z->parent->right;
        }

        W->isBlack = Z->parent->isBlack;
        Z->parent->isBlack = W->right->isBlack = true;
        rotateLeft ( Z->parent, Z->parent->parent );
        Z = root;
      }
      else
      {
        W = Z->parent->left;

        if( !W->isBlack)
        { 
          W->isBlack = true;
          Z->parent->isBlack = false;
          rotateRight ( Z->parent, Z->parent->parent );
          W = Z->parent->left;
        }

        if( ( W->left->isBlack) && ( W->right->isBlack) )
        { 
          W->isBlack = false;
          Z = Z->parent;
          continue;
        }

        if( W->left->isBlack)
        { 
          W->right->isBlack = true;
          W->isBlack = false;
          rotateLeft( W, W->parent );
          W = Z->parent->left;
        }

        W->isBlack = Z->parent->isBlack; 
        Z->parent->isBlack = true;
        W->left->isBlack = true;
        rotateRight ( Z->parent, Z->parent->parent );
        Z = root; 
      }

  Z->isBlack = true;

  delete Y;
}