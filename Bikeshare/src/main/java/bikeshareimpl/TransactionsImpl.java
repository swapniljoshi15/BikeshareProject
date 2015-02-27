package bikeshareimpl;

import bikeshareinterfaces.TransactionsInterface;

import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import controller.BikeShareController;
import resources.Transactions;
import util.BikeShareUtil;
import util.MongodbConnection;

import java.util.List;

/**
 * Created by gaurav on 12/6/14.
 */
public class TransactionsImpl implements TransactionsInterface{

    private MongoTemplate mongoTemplate = MongodbConnection.getConnection();

    @Override
    public void addTransaction(Transactions transactions) {

        transactions.setBooking_status(BikeShareController.globalReservationIndicator);
        transactions.setDate_of_booking(DateTime.now());
        transactions.setCreated_date(DateTime.now());
        transactions.setUpdated_date(DateTime.now());

        mongoTemplate.insert(transactions);
    }

    @Override
    public Transactions getTransaction(int transactions_id) {
        return mongoTemplate.findOne(new Query((Criteria.where("transaction_id").is(transactions_id))), Transactions.class);
    }
    
    /*public int getMaxTransactionID() {
        return mongoTemplate.find(sort({$max("transaction_id").is(transactions_id))), Transactions.class);
    }*/
    
    

    @Override
    public List<Transactions> getAllTransactions() {
        return mongoTemplate.findAll(Transactions.class);
    }

    @Override
    public void updateTransaction(int transactions_id) {

        Update update = new Update();
        update.set("updated_date",DateTime.now());
        update.set("booking_status",BikeShareController.globalCancellationIndicator);
        mongoTemplate.updateFirst(new Query(Criteria.where("transaction_id").is(transactions_id)), update, Transactions.class);
    }

    @Override
    public List<Transactions> getUserTransactions(int user_id) {
        
    	Query query = new Query((Criteria.where("user_id").is(user_id)));
    	query.with(new Sort(Sort.Direction.DESC, "date_of_booking"));
    	
    	return mongoTemplate.find(query, Transactions.class);
    }
}
