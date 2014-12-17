package com.cafeinelabs.charadas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cafeinelabs.charadas.dummy.DummyContent;
import com.cafeinelabs.charadas.dummy.DummyContent.DummyItem;

/**
 * An activity representing a list of MakeCards. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link MakeCardDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link MakeCardListFragment} and the item details (if present) is a
 * {@link MakeCardDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link MakeCardListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class MakeCardListActivity extends FragmentActivity implements
		MakeCardListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_makecard_list);

		if (findViewById(R.id.makecard_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((MakeCardListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.makecard_list))
					.setActivateOnItemClick(true);
		}
		
		if (DummyContent.ITEMS.isEmpty()) {
			DummyContent.addItem(new DummyItem("0", getResources().getString(R.string.juego_r_pido)));
			DummyContent.addItem(new DummyItem("1", getResources().getString(R.string.pel_culas)));
			DummyContent.addItem(new DummyItem("2", getResources().getString(R.string.personajes)));
			DummyContent.addItem(new DummyItem("3", getResources().getString(R.string.video_juegos)));
			DummyContent.addItem(new DummyItem("4", getResources().getString(R.string.pa_ses)));
			DummyContent.addItem(new DummyItem("5", getResources().getString(R.string.animales)));
			DummyContent.addItem(new DummyItem("6", getResources().getString(R.string.artistas)));
			DummyContent.addItem(new DummyItem("7", getResources().getString(R.string._actualo_)));
			DummyContent.addItem(new DummyItem("8", getResources().getString(R.string.super_heroes)));
			DummyContent.addItem(new DummyItem("9", getResources().getString(R.string.series_de_tv)));
			DummyContent.addItem(new DummyItem("10", getResources().getString(R.string.series_para_ni_os)));
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link MakeCardListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(MakeCardDetailFragment.ARG_ITEM_ID, id);
			MakeCardDetailFragment fragment = new MakeCardDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.makecard_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, MakeCardDetailActivity.class);
			detailIntent.putExtra(MakeCardDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		MakeCardListActivity.this.finish();
	}
}
