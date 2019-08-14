package com.example.alexr.ideamanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexr.ideamanager.helpers.SampleContent;
import com.example.alexr.ideamanager.models.Idea;
import com.example.alexr.ideamanager.services.IdeaService;
import com.example.alexr.ideamanager.services.ServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdeaDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Idea mItem;

    public IdeaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.idea_detail, container, false);

        final Context context = getContext();

        Button updateIdea = (Button) rootView.findViewById(R.id.idea_update);
        Button deleteIdea = (Button) rootView.findViewById(R.id.idea_delete);

        final EditText ideaName = (EditText) rootView.findViewById(R.id.idea_name);
        final EditText ideaDescription = (EditText) rootView.findViewById(R.id.idea_description);
        final EditText ideaStatus = (EditText) rootView.findViewById(R.id.idea_status);
        final EditText ideaOwner = (EditText) rootView.findViewById(R.id.idea_owner);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Activity activity = this.getActivity();
            final CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            IdeaService ideaService = ServiceBuilder.buildService(IdeaService.class);
            Call<Idea> request = ideaService.getIdea(getArguments().getInt(ARG_ITEM_ID));

            request.enqueue(new Callback<Idea>() {
                @Override
                public void onResponse(Call<Idea> request, Response<Idea> response) {
                    mItem = response.body();

                    ideaName.setText(mItem.getName());
                    ideaDescription.setText(mItem.getDescription());
                    ideaOwner.setText(mItem.getOwner());
                    ideaStatus.setText(mItem.getStatus());

                    if (appBarLayout != null) {
                        appBarLayout.setTitle(mItem.getName());
                    }
                }

                @Override
                public void onFailure(Call<Idea> request, Throwable t) {
                    Toast.makeText(context, "Failed to retrieve item.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        updateIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Idea newIdea = new Idea();
                newIdea.setId(getArguments().getInt(ARG_ITEM_ID));
                newIdea.setName(ideaName.getText().toString());
                newIdea.setDescription(ideaDescription.getText().toString());
                newIdea.setStatus(ideaStatus.getText().toString());
                newIdea.setOwner(ideaOwner.getText().toString());

                SampleContent.updateIdea(newIdea);
                Intent intent = new Intent(getContext(), IdeaListActivity.class);
                startActivity(intent);
            }
        });

        deleteIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SampleContent.deleteIdea(getArguments().getInt(ARG_ITEM_ID));
                Intent intent = new Intent(getContext(), IdeaListActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
