package com.example.money.conatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.example.money.conatus.View.MyTextView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;
import java.util.List;

public class LandingActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;
    private RecyclerView mMenuRecyclerView;
    private Toolbar mToolbar;
    private FrameLayout mMainPage;
    private List<Menu> mMenuList = new ArrayList<>();
    private MyTextView mToolbarTitle;
    private ImageView mToolbarContextMenu;
    private FragmentManager mFragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private GuillotineAnimation mGullotineAnimation;
    private final static String HOME_FRAGMENT = "home";
    private final static String EVENT_FRAGMENT = "event";
    private final static String MAGAZINE_FRAGMENT = "magazine";
    private final static String GALLERY_FRAGMENT = "galley";
    private final static String TEAM_FRAGMENT = "team";
    private final static String ABOUT_FRAGMENT = "about";
    private final static String CONTACT_FRAGMENT = "contact";
    private final static String QUERY_FRAGMENT = "query";
    private HomeFragment mHomeFragment;
    private ContactFragment mContactFragment;
    private AboutUsFragment mAboutUsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setResource();
        initMenuFragment();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(null);
        }
        mMainPage = (FrameLayout) findViewById(R.id.root);
        View menu = LayoutInflater.from(this).inflate(R.layout.main_menu_layout, null);
        mMenuRecyclerView = (RecyclerView) menu.findViewById(R.id.menu_list);
        mFragmentManager = getSupportFragmentManager();
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMenuRecyclerView.setAdapter(new MenuAdapter());

        mMainPage.addView(menu);
        mToolbarContextMenu = (ImageView) findViewById(R.id.context_menu);
        mToolbarTitle = (MyTextView) findViewById(R.id.toolbar_title);

        mGullotineAnimation = new GuillotineAnimation.GuillotineBuilder(menu, menu.findViewById(R.id.menu_image_closed), findViewById(R.id.menu_image))
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(mToolbar)
                .setClosedOnStart(false)
                .build();
        mHomeFragment = new HomeFragment();
        mFragmentManager.beginTransaction().add(R.id.main_fragment_container, mHomeFragment, HOME_FRAGMENT).commit();

        mToolbarContextMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction().add(mMenuDialogFragment,ContextMenuDialogFragment.TAG).commit();
            }
        });
        RecyclerViewHeader header = (RecyclerViewHeader) menu.findViewById(R.id.header);
        header.attachTo(mMenuRecyclerView);

    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View clickedView, int position) {
                if(position!=0)
                 fragmentChange(position,false,null);
            }
        });
    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.cancel);

        MenuObject about = new MenuObject("ABOUT US");
        about.setResource(R.drawable.aboutus);

        MenuObject contact = new MenuObject("CONTACT US");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.agenda);
        contact.setBitmap(b);

        MenuObject query = new MenuObject("QUERY US");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.query));
        query.setDrawable(bd);

        menuObjects.add(close);
        menuObjects.add(about);
        menuObjects.add(contact);
        menuObjects.add(query);
        return menuObjects;
    }


    private void setResource() {

        int imageId[] = {
                R.mipmap.ic_home_black_24dp, R.mipmap.ic_event_black_24dp, R.mipmap.ic_book_black_24dp,
                R.mipmap.ic_wallpaper_black_24dp, R.mipmap.ic_group_black_24dp, R.mipmap.ic_contact_mail_black_24dp
        };
        String title[] = {
                "HOME", "EVENTS", "MAGAZINES", "GALLERY", "TEAM", "CONTACT US"
        };

        for (int i = 0; i < imageId.length; i++) {
            Menu menu = new Menu(title[i], imageId[i]);
            mMenuList.add(menu);
        }

    }

    private class Menu {
        String title;
        int imageid;

        public Menu(String title, int imageId) {
            this.title = title;
            this.imageid = imageId;
        }

        private String getTitle() {

            return title;
        }


        private int getImageid() {
            return imageid;
        }


    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
        @Override
        public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(R.layout.menu_list_layout, parent, false);
            return new MenuViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MenuViewHolder holder, int position) {
            holder.menuIcon.setImageResource(mMenuList.get(position).getImageid());
            holder.menuTitle.setText(mMenuList.get(position).getTitle());
            holder.pos = position;

        }

        @Override
        public int getItemCount() {
            return mMenuList.size();
        }
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView menuIcon;
        private MyTextView menuTitle;
        private int pos;

        private MenuViewHolder(View itemView) {
            super(itemView);
            menuIcon = (ImageView) itemView.findViewById(R.id.menu_icon);
            menuTitle = (MyTextView) itemView.findViewById(R.id.icon_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentChange(pos, true, menuTitle.getText().toString());

                }
            });
        }

    }

    private void fragmentChange(int position, boolean fromDrawer, String title) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragmentList = mFragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            if(fragment!=mFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG))
            transaction.hide(fragment);
        }
            if (fromDrawer) {
                mToolbarTitle.setText(title);
                switch (position) {
                    case 0:
                        if (mHomeFragment == null) {
                            mHomeFragment = new HomeFragment();
                            transaction.add(R.id.main_fragment_container, mHomeFragment, HOME_FRAGMENT);
                        } else {
                            transaction.show(mFragmentManager.findFragmentByTag(HOME_FRAGMENT));
                        }
                        break;
                    case 5:
                        if (mContactFragment == null) {
                            mContactFragment = new ContactFragment();
                            transaction.add(R.id.main_fragment_container, mContactFragment, CONTACT_FRAGMENT);
                        } else {
                            transaction.show(mFragmentManager.findFragmentByTag(CONTACT_FRAGMENT));
                        }
                        break;
                }
                mGullotineAnimation.close();
            } else {
                switch (position) {
                    case 1:
                        mToolbarTitle.setText("ABOUT US");
                        if (mAboutUsFragment == null) {
                            mAboutUsFragment = new AboutUsFragment();
                            transaction.add(R.id.main_fragment_container, mAboutUsFragment, ABOUT_FRAGMENT);
                        } else {
                            transaction.show(mFragmentManager.findFragmentByTag(ABOUT_FRAGMENT));
                        }
                        break;
                    case 2:
                        mToolbarTitle.setText("CONTACT US");
                        if (mContactFragment == null) {
                            mContactFragment = new ContactFragment();
                            transaction.add(R.id.main_fragment_container, mContactFragment, CONTACT_FRAGMENT);
                        } else {
                            transaction.show(mFragmentManager.findFragmentByTag(CONTACT_FRAGMENT));
                        }
                        break;
                    case 3:
                        mToolbarTitle.setText("QUERY US");
                        break;
                }
            }


            transaction.commit();


    }
}
