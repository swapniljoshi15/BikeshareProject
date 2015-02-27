package bikeshareinterfaces;

import resources.Transactions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 12/6/14.
 */
public interface TransactionsInterface {

    public void addTransaction(Transactions transactions);
    public Transactions getTransaction(int transactions_id);
    public List<Transactions> getAllTransactions();
    public void updateTransaction(int transactions_id);
    public List<Transactions> getUserTransactions(int user_id);

}