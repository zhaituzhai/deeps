

concat(' UPDATE t_sim_original_bill SET finvoiceamount = ( SELECT SUM(FAMOUNT) FROM t_sim_original_bill_item WHERE fid = ', ID,
' ), ftotaltax = ( SELECT SUM(FTAX) FROM t_sim_original_bill_item WHERE fid = ',ID ,
' ),  FTOTALAMOUNT = ( SELECT SUM(FTAXAMOUNT) FROM t_sim_original_bill_item WHERE fid = ', ID,
' ) WHERE  fid = ', ID);

CREATE PROCEDURE modify_amount(ID int)
begin
declare stmt varchar(2000);
      BEGIN
         SET @sqlstr = concat(' UPDATE t_sim_original_bill SET finvoiceamount = ( SELECT SUM(FAMOUNT) FROM t_sim_original_bill_item WHERE fid = ', ID,
							' ), ftotaltax = ( SELECT SUM(FTAX) FROM t_sim_original_bill_item WHERE fid = ',ID ,
							' ),  FTOTALAMOUNT = ( SELECT SUM(FTAXAMOUNT) FROM t_sim_original_bill_item WHERE fid = ', ID,
							' ) WHERE  fid = ', ID);
		end;
prepare stmt from @sqlstr;
execute stmt;
END;
