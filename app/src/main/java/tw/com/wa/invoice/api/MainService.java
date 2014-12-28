package tw.com.wa.invoice.api;

import android.content.Context;

import tw.com.wa.invoice.domain.MainDTO;

/**
 * Created by Andy on 14/12/28.
 */
public interface MainService {


   void loadPage(MainDTO dto,Context context)throws RuntimeException;
}
