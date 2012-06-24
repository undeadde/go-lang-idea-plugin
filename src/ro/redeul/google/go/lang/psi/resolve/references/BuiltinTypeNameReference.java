package ro.redeul.google.go.lang.psi.resolve.references;

import java.util.Collection;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.NotNull;
import ro.redeul.google.go.lang.psi.GoFile;
import ro.redeul.google.go.lang.psi.processors.GoResolveStates;
import ro.redeul.google.go.lang.psi.resolve.TypeNameResolver;
import ro.redeul.google.go.lang.psi.toplevel.GoTypeNameDeclaration;
import ro.redeul.google.go.lang.psi.types.GoTypeName;
import ro.redeul.google.go.lang.psi.utils.GoPsiUtils;
import ro.redeul.google.go.lang.stubs.GoNamesCache;

public class BuiltinTypeNameReference extends TypeNameReference {

    public BuiltinTypeNameReference(GoTypeName element) {
        super(element);
    }

    @Override
    public PsiElement resolve() {

        TypeNameResolver processor = new TypeNameResolver(this);

        GoNamesCache namesCache = GoNamesCache.getInstance(getElement().getProject());

        // get the file included in the imported package name
        Collection<GoFile> files =
            namesCache.getFilesByPackageName(
                GoPsiUtils.cleanupImportPath("builtin"));


        for (GoFile file : files) {
            ResolveState newState =
                GoResolveStates.imported("builtin", "");

            if (!file.processDeclarations(processor, newState, null, getElement()))  {
                break;
            }
        }

        return processor.getDeclaration();
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        if (element instanceof GoTypeNameDeclaration) {
            GoTypeNameDeclaration nameDeclaration = (GoTypeNameDeclaration)element;
            return getElement().getText().equals(nameDeclaration.getName());
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}
