package ro.ac.upt.christmasarsample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode


class AugmentedActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment

    private var renderable: Renderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_augmented)

        initRenderableModel()

        arFragment = supportFragmentManager.findFragmentById(R.id.scf_central) as ArFragment

        arFragment.setOnTapArPlaneListener(fun(result: HitResult, plane: Plane, motionEvent: MotionEvent) {
            val anchor = result.createAnchor()
            renderable?.let{
                addRenderableToScene(anchor, it)
            }
        })
    }

    private fun initRenderableModel() {
        val modelUri = Uri.parse("model.sfb")

        renderable = ModelRenderable.builder()
                                    .setSource(this, modelUri)
                                    .build()
                                    .get()

    }

    private fun addRenderableToScene(anchor: Anchor, renderable: Renderable) {

        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)

        val transformableNode = TransformableNode(arFragment.transformationSystem)
        transformableNode.setParent(anchorNode)

        transformableNode.renderable = renderable

    }

    companion object {
        private val TAG = AugmentedActivity::class.java.simpleName
    }

}
