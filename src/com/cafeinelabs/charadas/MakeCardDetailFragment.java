package com.cafeinelabs.charadas;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cafeinelabs.charadas.db.Card;
import com.cafeinelabs.charadas.db.CardsDataSource;
import com.cafeinelabs.charadas.db.DbOpenHelper;
import com.cafeinelabs.charadas.dummy.DummyContent;
import com.cafeinelabs.charadas.utils.AppConstants;
import com.cafeinelabs.charadas.utils.SwipeDismissListViewTouchListener;

/**
 * A fragment representing a single MakeCard detail screen. This fragment is
 * either contained in a {@link MakeCardListActivity} in two-pane mode (on
 * tablets) or a {@link MakeCardDetailActivity} on handsets.
 */
public class MakeCardDetailFragment extends Fragment {

	private CardsDataSource dataSource;
	private Context context;

	private String currentTable;
	private List<Card> cards;

	boolean theUserAddAnewCard = false;

	private ArrayList<String> cardsTitlesInCurrentList;
	private ArrayList<String> cardsTitlesInCurrentTable;

	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public MakeCardDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_makecard_detail,
				container, false);

		context = rootView.getContext();

		//Database
		dataSource = new CardsDataSource(context);
		dataSource.open();

		cardsTitlesInCurrentList = new ArrayList<String>();
		cardsTitlesInCurrentTable = new ArrayList<String>();

		final EditText etCard = (EditText) rootView.findViewById(R.id.editTextCard);

		final ListView mListView = (ListView) rootView.findViewById(android.R.id.list);

		final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>
		(context, android.R.layout.simple_list_item_checked, cardsTitlesInCurrentList);
		mListView.setAdapter(mAdapter);
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// Create a ListView-specific touch listener. ListViews are given special treatment because
		// by default they handle touches for their list items... i.e. they're in charge of drawing
		// the pressed state (the list selector), handling list item clicks, etc.
		SwipeDismissListViewTouchListener touchListener =
				new SwipeDismissListViewTouchListener(
						mListView,
						new SwipeDismissListViewTouchListener.DismissCallbacks() {
							@Override
							public boolean canDismiss(int position) {
								return true;
							}

							@Override
							public void onDismiss(ListView listView, int[] reverseSortedPositions) {
								for (int position : reverseSortedPositions) {
									String card = mAdapter.getItem(position);
									if (cardsTitlesInCurrentTable.contains(card)) {
										dataSource.removeCard(currentTable, cards.get(position));
										cards.remove(position);
									}
									Toast.makeText(context, card + getString(R.string._borrado), Toast.LENGTH_LONG).show();
									mAdapter.remove(card);
								}
								mAdapter.notifyDataSetChanged();
							}
						});
		mListView.setOnTouchListener(touchListener);
		// Setting this scroll listener is required to ensure that during ListView scrolling,
		// we don't look for swipes.
		mListView.setOnScrollListener(touchListener.makeScrollListener());

		Button addCardButton = (Button) rootView.findViewById(R.id.addCardButton);
		addCardButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				String cardName = etCard.getText().toString();
				cardName = cardName.trim();
				if (cardName.length() == 0) {
					Toast.makeText(context, R.string.escriba_el_nombre_de_la_nueva_carta_, Toast.LENGTH_LONG).show();
				} else if (cardsTitlesInCurrentList.contains(cardName)) {
					Toast.makeText(context, cardName + getString(R.string._ya_esta_en_la_lista_), Toast.LENGTH_LONG).show();
				} else {
					cardsTitlesInCurrentList.add(cardName);
					mAdapter.notifyDataSetChanged();
					mListView.setSelection(mListView.getAdapter().getCount()-1);
					etCard.setText("");
					theUserAddAnewCard = true;
				}
			}
		});

		Button saveCardsButton = (Button) rootView.findViewById(R.id.saveCardsButton);
		saveCardsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				final ArrayList<String> cardsChecked = getListItemsChecked(mListView, cardsTitlesInCurrentList);
				if (cardsTitlesInCurrentList.size() == 0) {
					Toast.makeText(context, R.string.agrega_una_nueva_carta_primero_, Toast.LENGTH_LONG).show();
				} else if (cardsChecked.size() == 0) {
					Toast.makeText(context, R.string.seleccione_las_cartas_a_guardar_, Toast.LENGTH_LONG).show();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(context)
					.setTitle(R.string.guardar)
					.setMessage(R.string.las_cartas_nuevas_solo_se_guardaran_si_las_ha_seleccionado_)
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							for (String card : cardsChecked) {
								dataSource.saveCard(currentTable, card);
							}
							theUserAddAnewCard = false;
						}
					})
					.setNegativeButton(android.R.string.cancel, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});

		Button playButton = (Button) rootView.findViewById(R.id.playButton);
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playSound();
				final ArrayList<String> cardsChecked = getListItemsChecked(mListView, cardsTitlesInCurrentList);
				if (cardsChecked.size() == 0) {
					Toast.makeText(context, R.string.seleccione_las_cartas_para_jugar_, Toast.LENGTH_LONG).show();
				} else if (theUserAddAnewCard) {
					AlertDialog.Builder builder = new AlertDialog.Builder(context)
					.setTitle(R.string.recuerde_guardar_las_nuevas_cartas_antes_de_jugar_)
					.setMessage("Las cartas nuevas solo se guardaran si las ha seleccionado, ¿desea guardar las cartas seleccionadas y continuar?")
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							for (String card : cardsChecked) {
								dataSource.saveCard(currentTable, card);
							}
							Intent intent = new Intent(context, TimerActivity.class);
							intent.putStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME, cardsChecked);
							startActivity(intent);
						}
					})
					.setNegativeButton(R.string.solo_jugar, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(context, TimerActivity.class);
							intent.putStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME, cardsChecked);
							startActivity(intent);
						}
					});
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					Intent intent = new Intent(context, TimerActivity.class);
					intent.putStringArrayListExtra(AppConstants.KEY_CUSTOM_THEME, cardsChecked);
					startActivity(intent);
				} 
			}
		});

		if (mItem != null) {

			String key = mItem.id;
			String hint = mItem.content;

			etCard.setHint(hint);

			switch (key) {

			case "0":
				currentTable = DbOpenHelper.TABLE_QUICK_PLAY;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "1":
				currentTable = DbOpenHelper.TABLE_MOVIES;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "2":
				currentTable = DbOpenHelper.TABLE_CHARACTERS;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "3":
				currentTable = DbOpenHelper.TABLE_VIDEO_GAMES;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "4":
				currentTable = DbOpenHelper.TABLE_COUNTRIES;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "5":
				currentTable = DbOpenHelper.TABLE_ANIMALS;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "6":
				currentTable = DbOpenHelper.TABLE_ARTISTS;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "7":
				currentTable = DbOpenHelper.TABLE_ACT_IT_OUT;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "8":
				currentTable = DbOpenHelper.TABLE_SUPER_HEROES;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "9":
				currentTable = DbOpenHelper.TABLE_TV_SHOWS;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			case "10":
				currentTable = DbOpenHelper.TABLE_TV_SHOWS_FOR_KIDS;
				cards = dataSource.findAll(currentTable);
				cardsTitlesInCurrentList.clear();
				cardsTitlesInCurrentTable.clear();
				for (Card card : cards) {
					cardsTitlesInCurrentList.add(card.getTitle());
					cardsTitlesInCurrentTable.add(card.getTitle());
				}
				mAdapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		}

		return rootView;
	}

	//The click sound
	private void playSound(){
		MediaPlayer click = MediaPlayer.create(context, R.raw.button_click);
		click.start();
		click.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	//Get the checked items in the list
	private ArrayList<String> getListItemsChecked(ListView listView, ArrayList<String> cardsList){
		SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();
		ArrayList<String> cardsChecked = new ArrayList<String>();
		for (int i = 0; i < listView.getCount() ; i++) {
			if (checkedPositions.get(i)) {
				cardsChecked.add(cardsList.get(i));
			}
		}
		return cardsChecked;
	}

	@Override
	public void onPause() {
		super.onPause();
		dataSource.close();
	}
}
