package awab.quran.ar.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import awab.quran.ar.R
import awab.quran.ar.ui.theme.*
import awab.quran.ar.ui.components.IslamicPatternBackground
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val auth = FirebaseAuth.getInstance()
    
    fun validateEmail(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun login() {
        emailError = null
        passwordError = null
        
        if (!validateEmail(email)) {
            emailError = "البريد الإلكتروني غير صحيح"
            return
        }
        
        if (!validatePassword(password)) {
            passwordError = "كلمة المرور يجب أن تكون 6 أحرف على الأقل"
            return
        }
        
        isLoading = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isLoading = false
                onLoginSuccess()
            }
            .addOnFailureListener { exception ->
                isLoading = false
                Toast.makeText(
                    context,
                    "خطأ في تسجيل الدخول: ${exception.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    IslamicPatternBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            // شعار التطبيق - أيقونة القرآن
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = Color.Transparent,
                border = BorderStroke(2.dp, PatternGold.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "📖",
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // العنوان الرئيسي
            Text(
                text = "تسميع القرآن",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = PatternBrownDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // النص الوصفي
            Text(
                text = "تحفِّظُكَ القرآنَ الكريمَ الذِّكْرُ الحكيمُ الذكاءُ الاصطناعي.",
                fontSize = 15.sp,
                color = PatternBrown.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // البطاقة الرئيسية
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp, bottomStart = 24.dp, bottomEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = PatternWhite.copy(alpha = 0.92f)),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // حقل البريد الإلكتروني
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; emailError = null },
                        placeholder = { Text("البريد الإلكتروني", color = PatternBrown.copy(alpha = 0.5f)) },
                        leadingIcon = { 
                            Icon(
                                Icons.Default.Email, 
                                "Email", 
                                tint = PatternGold.copy(alpha = 0.7f)
                            ) 
                        },
                        isError = emailError != null,
                        supportingText = emailError?.let { { Text(it) } },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email, 
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = PatternGold.copy(alpha = 0.6f),
                            unfocusedBorderColor = PatternGold.copy(alpha = 0.3f),
                            focusedTextColor = PatternBrownDark,
                            unfocusedTextColor = PatternBrownDark
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // حقل كلمة المرور
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; passwordError = null },
                        placeholder = { Text("••••••••", color = PatternBrown.copy(alpha = 0.5f)) },
                        leadingIcon = { 
                            Icon(
                                Icons.Default.Lock, 
                                "Password", 
                                tint = PatternGold.copy(alpha = 0.7f)
                            ) 
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    if (passwordVisible) "إخفاء" else "إظهار",
                                    tint = PatternGold.copy(alpha = 0.7f)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = passwordError != null,
                        supportingText = passwordError?.let { { Text(it) } },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password, 
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus(); login() }
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = PatternGold.copy(alpha = 0.6f),
                            unfocusedBorderColor = PatternGold.copy(alpha = 0.3f),
                            focusedTextColor = PatternBrownDark,
                            unfocusedTextColor = PatternBrownDark
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // نسيت كلمة المرور
                    TextButton(
                        onClick = onNavigateToForgotPassword,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            "• نسيت كلمة المرور؟",
                            color = PatternBrown.copy(alpha = 0.7f),
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // زر تسجيل الدخول
                    Button(
                        onClick = { login() },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7A8B6F),
                            contentColor = Color.White
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White, 
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                "تسجيل الدخول",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // أو تابع التسجيل باستخدام
            Text(
                text = "أو تابع التسجيل باستخدام",
                fontSize = 14.sp,
                color = PatternBrown.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // أزرار Social Login مع الأيقونات الحقيقية
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // زر Google
                SocialLoginButton(
                    iconResId = R.drawable.ic_google,
                    contentDescription = "Google",
                    onClick = {
                        Toast.makeText(context, "تسجيل الدخول بـ Google قريباً", Toast.LENGTH_SHORT).show()
                    }
                )

                // زر Apple
                SocialLoginButton(
                    iconResId = R.drawable.ic_apple,
                    contentDescription = "Apple",
                    onClick = {
                        Toast.makeText(context, "تسجيل الدخول بـ Apple قريباً", Toast.LENGTH_SHORT).show()
                    }
                )

                // زر Facebook
                SocialLoginButton(
                    iconResId = R.drawable.ic_facebook,
                    contentDescription = "Facebook",
                    onClick = {
                        Toast.makeText(context, "تسجيل الدخول بـ Facebook قريباً", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SocialLoginButton(
    iconResId: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(56.dp),
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 4.dp,
        border = BorderStroke(1.dp, PatternGold.copy(alpha = 0.2f))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = contentDescription,
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified  // للحفاظ على الألوان الأصلية للأيقونة
            )
        }
    }
}
